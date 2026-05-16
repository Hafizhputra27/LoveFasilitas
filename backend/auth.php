<?php
/**
 * auth.php — Login & Register API
 *
 * Endpoints:
 *   POST /auth.php?action=login    → login (JSON body: email, password)
 *   POST /auth.php?action=register → register (JSON body: username, name, email, phone, password)
 */

require_once __DIR__ . '/config.php';

$method = $_SERVER['REQUEST_METHOD'];
if ($method !== 'POST') {
    errorResponse(405, 'Method not allowed. Gunakan POST.');
}

if (!isset($_GET['action'])) {
    errorResponse(400, 'Parameter action diperlukan (login atau register)');
}

$action = $_GET['action'];

switch ($action) {
    case 'login':
        handleLogin();
        break;
    case 'register':
        handleRegister();
        break;
    default:
        errorResponse(400, "Action '$action' tidak dikenal");
}

// === LOGIN ===
function handleLogin() {
    global $conn;

    $input = getJsonInput();

    if (empty($input['email']) || empty($input['password'])) {
        errorResponse(400, 'Email dan password wajib diisi');
    }

    $email    = $conn->real_escape_string($input['email']);
    $password = $input['password'];

    $stmt = $conn->prepare('SELECT id, username, name, email, phone, password FROM users WHERE email = ?');
    $stmt->bind_param('s', $email);
    $stmt->execute();
    $result = $stmt->get_result();
    $user = $result->fetch_assoc();

    if (!$user) {
        errorResponse(401, 'Email atau password salah');
    }

    if (!password_verify($password, $user['password'])) {
        errorResponse(401, 'Email atau password salah');
    }

    unset($user['password']);
    successResponse($user, 'Login berhasil');
}

// === REGISTER ===
function handleRegister() {
    global $conn;

    $input = getJsonInput();

    $required = ['username', 'name', 'email', 'password'];
    foreach ($required as $field) {
        if (empty($input[$field])) {
            errorResponse(400, "Field '$field' wajib diisi");
        }
    }

    $username = $conn->real_escape_string($input['username']);
    $name     = $conn->real_escape_string($input['name']);
    $email    = $conn->real_escape_string($input['email']);
    $phone    = $conn->real_escape_string($input['phone'] ?? '');
    $password = password_hash($input['password'], PASSWORD_BCRYPT);

    // Cek username / email duplicate
    $check = $conn->query("SELECT id FROM users WHERE email = '$email' OR username = '$username'");
    if ($check->num_rows > 0) {
        errorResponse(409, 'Email atau username sudah terdaftar');
    }

    $stmt = $conn->prepare(
        'INSERT INTO users (username, name, email, phone, password) VALUES (?, ?, ?, ?, ?)'
    );
    $stmt->bind_param('sssss', $username, $name, $email, $phone, $password);

    if ($stmt->execute()) {
        $newId = $conn->insert_id;
        $result = $conn->query("SELECT id, username, name, email, phone FROM users WHERE id = $newId");
        successResponse($result->fetch_assoc(), 'Registrasi berhasil', 201);
    }
    errorResponse(500, 'Gagal registrasi: ' . $stmt->error);
}
