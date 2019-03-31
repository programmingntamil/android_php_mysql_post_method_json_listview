<?php
   $con=mysqli_connect("localhost","root","","android_test");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $username = $_POST['username'];
   $password = $_POST['password'];
   $result = mysqli_query($con,"SELECT * FROM login_data where username='$username' and password='$password'");
   $row = mysqli_fetch_array($result);
   $data = $row[0];

   if($data)
   {
			$result_courier = mysqli_query($con,"select * from courier_pending_today");
			if(mysqli_num_rows($result_courier) > 0)
			{
				$iteration = 0;
				echo '{';
				while($row = mysqli_fetch_assoc($result_courier))
				{
					$iteration++;
					if($iteration == mysqli_num_rows($result_courier))
						echo '"'.$row["Courier_ID"].'":"'.$row["Delivery_Status"].'"';
					else
				echo '"'.$row["Courier_ID"].'":"'.$row["Delivery_Status"].'",';
				}
				echo '}';
				
			}
			
	   
      //echo $data;
   }
   else
   {
   echo "Invalid User";
   }
   mysqli_close($con);
?>