<?php

include 'login_db_connect.php';
include 'login_functions.php';

sec_session_start();

if (isset($_POST['username'], $_POST['p'])) {
	$username = $_POST['username'];
	$password = $_POST['p'];
	if (login($username, $password, $mysqli_login) == true) {
		echo 'Success: You have logged in!';
	} else {
		header('Location: ./login.php?error=1');
	}
} else {
	echo 'Invalid Request';
}
?>