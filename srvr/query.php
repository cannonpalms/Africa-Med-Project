<?php 
include 'app_functions.php';
include 'login_functions.php';
include 'login_db_connect.php';
include 'app_db_connect.php';

sec_session_start();

if (login_check($mysqli_login) == true) {
  
	$patient_id = $_GET['id'];
    $json = query_patient($patient_id, $mysqli_app);
	print($json);
} else {
	echo 'You are not authorized to access this page, please login';
}
?>