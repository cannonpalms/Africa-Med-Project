<?php
set_include_path(get_include_path() . PATH_SEPARATOR . '../dependencies/phpseclib/phpseclib');

include('Net/SSH2.php');
include('Crypt/RSA.php');

$rsa = new Crypt_RSA();

$key = $rsa->createKey(1024);

$privatekey = $key['privatekey'];

print($privatekey);

?>