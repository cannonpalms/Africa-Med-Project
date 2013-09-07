<html>
	<head>
		<script type="text/javascript" src="sha512.js" />
		<script type="text/javascript" src="forms.js" />
	</head>
	
	<body>
	<?php
	include 'login_db_connect.php';
	include 'login_functions.php';

	sec_session_start();

	if (login_check($mysqli) == true) {
		print(
			'<form action="process_registration.php" method="POST" name="login_form">
			Username: <input type="text" name="username" /><br />
			Password: <input type="password" name="password" id="password"/><br />
			<input type="button" value="Login" onclick="formhash(this.form, this.form.password);" />
			</form>');
	} else {
		echo 'You are not logged in';
	}
	
	?>
	</body>
</html>