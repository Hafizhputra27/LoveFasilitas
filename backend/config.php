<?php
/**
 * config.php — Koneksi database + helper functions
 * Deploy ke InfinityFree, sesuaikan credentials di bawah
 */

// === DATABASE CONFIG (InfinityFree) ===
define('DB_HOST', 'sql313.infinityfree.com');
define('DB_USER', 'if0_41939528');
define('DB_PASS', 'BrgF9sLJaFl');
define('DB_NAME', 'if0_41939528_lovefasilitas_db');

// === KONEKSI ===
$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

if ($conn->connect_error) {
    errorResponse(500, 'Database connection failed: ' . $conn->connect_error);
    exit;
}

$conn->set_charset('utf8');

// === CORS HEADERS ===
header('Content-Type: application/json; charset=UTF-8');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit;
}

// === HELPER FUNCTIONS ===
function successResponse($data, $message = 'OK', $code = 200) {
    http_response_code($code);
    echo json_encode([
        'success' => true,
        'message' => $message,
        'data' => $data
    ], JSON_UNESCAPED_UNICODE);
    exit;
}

function errorResponse($code, $message, $data = null) {
    http_response_code($code);
    echo json_encode([
        'success' => false,
        'message' => $message,
        'data' => $data
    ], JSON_UNESCAPED_UNICODE);
    exit;
}

function getJsonInput() {
    $json = file_get_contents('php://input');
    return json_decode($json, true) ?: [];
}
