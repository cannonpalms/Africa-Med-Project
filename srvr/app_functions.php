<?php

function query_patient($patient_id, $mysqli)
{
	 
	$query = "SELECT * FROM patients WHERE patient_id=".$patient_id;
	$keys = array('patient_id', 'first_name', 'middle_name', 'last_name', 'gender', 'dob', 'allergies');
	 
	$result = $mysqli->query($query);
	$row = $result->fetch_row();
	array_push($row, query_allergies($patient_id, $mysqli));
	$map = array_combine($keys, $row);
	 
	return json_encode($map);
}

function query_allergies($patient_id, $mysqli)
{
	$query = "SELECT allergy_id FROM patient_allergies WHERE patient_id=".$patient_id;
	$key = array('allergies');
	$allergies = array();
	if ($result = $mysqli->query($query)) {
		while ($row = $result->fetch_row()) {
			$allergy_id = $row[0];
			array_push($allergies, get_allergy_name($allergy_id, $mysqli));
		}
		$result->close();
	}
	return $allergies;
}

function get_allergy_name($allergy_id, $mysqli)
{
	 
	$query = "SELECT allergy_name FROM allergies WHERE allergy_id=".$allergy_id;
	 
	$row = $mysqli->query($query)->fetch_row();
	 
	return $row[0];
}

?>