<!DOCTYPE html>
<html>
	<head>
		<title>Customers</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
	</head>
	<body>
		<h1>Customers</h1>
		
		
		<hr>
		<h2>Customers list</h2>
		<div>
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Surname</th>
						<th>Email</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<#list customers as customer>
					<tr>
						<td>${customer.name}</td>
						<td>
							<#if customer.surname??>
								${customer.surname}
							</#if>
						</td>

						<td>${customer.email}</td>
						<td>
							<a href="delete?id=${customer.id}">Delete</a>
							<a href="?id=${customer.id}">Edit</a>
						</td>
					</tr>
					</#list>
				</tbody>
			</table>
		</div>
	</body>
</html>