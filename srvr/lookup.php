<?php
include 'login_db_connect.php';
include 'login_functions.php';

sec_session_start();
if(login_check($mysqli_login) == true) {

	print('<form name="input" action="query.php" method="GET">
		ID: <input type="text" name="id" />
		<input type="submit" value="Go" />
	</form>');
} else {
	echo 'You are not authorized to access this page, please login. <br/>';
}
?>