<?php
// Verifica se sono stati inviati dati tramite il metodo POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Ottieni i dati inviati dal modulo
    $category = $_POST["category"];
    $problem = $_POST["problem"];
    
    // Se la categoria è "compiti", ottieni anche la materia
    if ($category === 'compiti') {
        $materia = $_POST["materia"];
    } 
    // Se la categoria è "insegnamento", ottieni anche il professore
    else if ($category === 'insegnamento') {
        $professore = $_POST["professore"];
    }
    
    // Formatta i dati da salvare
    $data = "Categoria: $category\nProblema: $problem\n";
    if (isset($materia)) {
        $data .= "Materia: $materia\n";
    }
    if (isset($professore)) {
        $data .= "Professore: $professore\n";
    }
    
    // Salva i dati nel file di testo
    $file = 'dati_form.txt';
    file_put_contents($file, $data, FILE_APPEND | LOCK_EX);
    
    // Invia una risposta JSON per indicare il successo dell'operazione
    $response = array("success" => true);
    header('Content-Type: application/json');
    echo json_encode($response);
} else {
    // Se non sono stati inviati dati tramite POST, restituisci un errore
    $response = array("success" => false, "error" => "Metodo non supportato");
    header('Content-Type: application/json');
    echo json_encode($response);
}
?>
