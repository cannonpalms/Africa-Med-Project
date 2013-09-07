<html>
	<head>
		<script type="text/javascript" src="sha512.js"></script>
		<script type="text/javascript" src="forms.js"></script>
	</head>
	<?php 
	if (isset($_GET['error'])) {
	echo 'Error Logging In!';
	}
	?>
	
	<body>
	
		<form action="process_login.php" method="POST" name="login_form">
			Username: <input type="text" name="username" /><br />
			Password: <input type="password" name="password" id="password"/><br />
			<input type="button" value="Login" onclick="formhash(this.form, this.form.password);" />
		</form>
	</body>
</html>