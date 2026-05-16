<?php
/**
 * facilities.php — REST API CRUD untuk fasilitas
 * 
 * Endpoints:
 *   GET  /facilities.php             → semua fasilitas
 *   GET  /facilities.php?id=1        → fasilitas by ID
 *   GET  /facilities.php?category=hall → filter by category
 *   POST /facilities.php             → tambah fasilitas (JSON body)
 *   POST /facilities.php?_method=PUT&id=1   → update fasilitas (InfinityFree workaround)
 *   POST /facilities.php?_method=DELETE&id=1 → hapus fasilitas (InfinityFree workaround)
 */

require_once __DIR__ . '/config.php';

$method = $_SERVER['REQUEST_METHOD'];

// InfinityFree: override method via _method param di POST request
if ($method === 'POST' && isset($_GET['_method'])) {
    $method = strtoupper($_GET['_method']);
}

switch ($method) {
    case 'GET':
        handleGet();
        break;
    case 'POST':
        handlePost();
        break;
    case 'PUT':
        handlePut();
        break;
    case 'DELETE':
        handleDelete();
        break;
    default:
        errorResponse(405, 'Method not allowed');
}

// === GET: ambil fasilitas ===
function handleGet() {
    global $conn;

    // GET by ID
    if (isset($_GET['id'])) {
        $id = intval($_GET['id']);
        $stmt = $conn->prepare('SELECT * FROM facilities WHERE id = ?');
        $stmt->bind_param('i', $id);
        $stmt->execute();
        $result = $stmt->get_result();
        $facility = $result->fetch_assoc();

        if (!$facility) {
            errorResponse(404, 'Fasilitas tidak ditemukan');
        }
        successResponse([$facility], 'Fasilitas ditemukan');
    }

    // GET by category
    if (isset($_GET['category'])) {
        $category = $conn->real_escape_string($_GET['category']);
        $stmt = $conn->prepare('SELECT * FROM facilities WHERE LOWER(category) = LOWER(?) ORDER BY created_at DESC');
        $stmt->bind_param('s', $category);
        $stmt->execute();
        $result = $stmt->get_result();
        $facilities = $result->fetch_all(MYSQLI_ASSOC);
        successResponse($facilities, 'OK');
    }

    // GET all
    $sql = 'SELECT * FROM facilities ORDER BY created_at DESC';
    $result = $conn->query($sql);
    $facilities = $result->fetch_all(MYSQLI_ASSOC);
    successResponse($facilities, 'OK');
}

// === POST: tambah fasilitas ===
function handlePost() {
    global $conn;

    $input = getJsonInput();

    $required = ['name', 'category', 'price', 'capacity', 'location'];
    foreach ($required as $field) {
        if (empty($input[$field])) {
            errorResponse(400, "Field '$field' wajib diisi");
        }
    }

    $name        = $conn->real_escape_string($input['name']);
    $category    = $conn->real_escape_string($input['category']);
    $price       = intval($input['price']);
    $capacity    = intval($input['capacity']);
    $location    = $conn->real_escape_string($input['location']);
    $description = $conn->real_escape_string($input['description'] ?? '');
    $rating      = floatval($input['rating'] ?? 0.0);

    $stmt = $conn->prepare(
        'INSERT INTO facilities (name, category, price, capacity, location, description, rating) VALUES (?, ?, ?, ?, ?, ?, ?)'
    );
    $stmt->bind_param('ssiissd', $name, $category, $price, $capacity, $location, $description, $rating);

    if ($stmt->execute()) {
        $newId = $conn->insert_id;
        $result = $conn->query("SELECT * FROM facilities WHERE id = $newId");
        successResponse($result->fetch_assoc(), 'Fasilitas berhasil ditambahkan', 201);
    }
    errorResponse(500, 'Gagal menambahkan fasilitas: ' . $stmt->error);
}

// === PUT: update fasilitas ===
function handlePut() {
    global $conn;

    if (!isset($_GET['id'])) {
        errorResponse(400, 'Parameter id diperlukan');
    }

    $id    = intval($_GET['id']);
    $input = getJsonInput();

    // Cek fasilitas exists
    $check = $conn->query("SELECT id FROM facilities WHERE id = $id");
    if ($check->num_rows === 0) {
        errorResponse(404, 'Fasilitas tidak ditemukan');
    }

    $fields = [];
    $params = [];
    $types  = '';

    if (isset($input['name'])) {
        $fields[] = 'name = ?';
        $params[] = $conn->real_escape_string($input['name']);
        $types   .= 's';
    }
    if (isset($input['category'])) {
        $fields[] = 'category = ?';
        $params[] = $conn->real_escape_string($input['category']);
        $types   .= 's';
    }
    if (isset($input['price'])) {
        $fields[] = 'price = ?';
        $params[] = intval($input['price']);
        $types   .= 'i';
    }
    if (isset($input['capacity'])) {
        $fields[] = 'capacity = ?';
        $params[] = intval($input['capacity']);
        $types   .= 'i';
    }
    if (isset($input['location'])) {
        $fields[] = 'location = ?';
        $params[] = $conn->real_escape_string($input['location']);
        $types   .= 's';
    }
    if (isset($input['description'])) {
        $fields[] = 'description = ?';
        $params[] = $conn->real_escape_string($input['description']);
        $types   .= 's';
    }
    if (isset($input['rating'])) {
        $fields[] = 'rating = ?';
        $params[] = floatval($input['rating']);
        $types   .= 'd';
    }

    if (empty($fields)) {
        errorResponse(400, 'Tidak ada field yang di-update');
    }

    $params[] = $id;
    $types   .= 'i';

    $sql = 'UPDATE facilities SET ' . implode(', ', $fields) . ' WHERE id = ?';
    $stmt = $conn->prepare($sql);
    $stmt->bind_param($types, ...$params);

    if ($stmt->execute()) {
        $result = $conn->query("SELECT * FROM facilities WHERE id = $id");
        successResponse($result->fetch_assoc(), 'Fasilitas berhasil diupdate');
    }
    errorResponse(500, 'Gagal mengupdate fasilitas: ' . $stmt->error);
}

// === DELETE: hapus fasilitas ===
function handleDelete() {
    global $conn;

    if (!isset($_GET['id'])) {
        errorResponse(400, 'Parameter id diperlukan');
    }

    $id = intval($_GET['id']);

    $check = $conn->query("SELECT id FROM facilities WHERE id = $id");
    if ($check->num_rows === 0) {
        errorResponse(404, 'Fasilitas tidak ditemukan');
    }

    if ($conn->query("DELETE FROM facilities WHERE id = $id")) {
        successResponse(['id' => $id], 'Fasilitas berhasil dihapus');
    }
    errorResponse(500, 'Gagal menghapus fasilitas');
}
