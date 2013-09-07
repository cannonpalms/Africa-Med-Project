<?php

function sec_session_start() {
	$session_name = "sec_session_id";
	$secure = false;
	$httponly = false;
	
	ini_set('session.use_only_cookies', 1);
	$cookieParams = session_get_cookie_params();
	session_set_cookie_params(86400, $cookieParams["path"], $cookieParams["domain"], $secure, $httponly);
	session_name($session_name);
	session_start();
	session_regenerate_id();
}

function login($username, $password, $mysqli) {
	if ($stmt = $mysqli->prepare("SELECT id, username, password, salt FROM members WHERE username = ? LIMIT 1")) {
		$stmt->bind_param('s', $username);
		$stmt->execute();
		$stmt->store_result();
		$stmt->bind_result($user_id, $username, $db_password, $salt);
		$stmt->fetch();
		$password = hash('sha512', $password.$salt);
		
		if ($stmt->num_rows() == 1) {
			if (checkbrute($user_id, $mysqli) == true) {
				
				return false;
			} else {
				
				if ($db_password == $password) {
					
					$user_browser = $_SERVER['HTTP_USER_AGENT'];
					
					$user_id = preg_replace("/[^0-9]+/", "", $user_id);
					$_SESSION['user_id'] = $user_id;
					$username = preg_replace("/[^a-zA-A0-9_\_]+/", "", $username);
					$_SESSION['username'] = $username;
					$_SESSION['login_string'] = hash('sha512', $password.$user_browser);
					
					return true;
				} else {
					$now = time();
					$mysqli->query("INSERT INTO login_attempts (user_id, time) VALUES ('$user_id', '$now')");
					return false;
				}
			}
		} else {
			return false;
		}
	}
}

function checkbrute($user_id, $mysqli) {
	$now = time();
	
	$valid_attempts = $now - (30 * 60);
	
	if ($stmt = $mysqli->prepare("SELECT time FROM login_attempts WHERE user_id = ? AND time > '$valid_attempts'")) {
		$stmt->bind_param('i', $user_id);
		$stmt->execute();
		$stmt->store_result();
		
		if ($stmt->num_rows() > 10) {
			return true;
		} else {
			return false;
		}
	}
}

function login_check($mysqli) {
	// Check if all session variables are set
	if(isset($_SESSION['user_id'], $_SESSION['username'], $_SESSION['login_string'])) {
		$user_id = $_SESSION['user_id'];
		$login_string = $_SESSION['login_string'];
		$username = $_SESSION['username'];

		$user_browser = $_SERVER['HTTP_USER_AGENT']; // Get the user-agent string of the user.

		if ($stmt = $mysqli->prepare("SELECT password FROM members WHERE id = ? LIMIT 1")) {
			$stmt->bind_param('i', $user_id); // Bind "$user_id" to parameter.
			$stmt->execute(); // Execute the prepared query.
			$stmt->store_result();

			if($stmt->num_rows == 1) { // If the user exists
				$stmt->bind_result($password); // get variables from result.
				$stmt->fetch();
				$login_check = hash('sha512', $password.$user_browser);
				if($login_check == $login_string) {
					// Logged In!!!!
					return true;
				} else {
					// Not logged in
					return false;
				}
			} else {
				// Not logged in
				return false;
			}
		} else {
			// Not logged in
			return false;
		}
	} else {
		// Not logged in
		return false;
	}
}

function user_is($username, $mysqli) {
	if (login_check($mysqli) == true) {
		if ($_SESSION['username'] == "admin") {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}
?>