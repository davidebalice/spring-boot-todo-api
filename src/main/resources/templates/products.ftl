<!DOCTYPE html>
<html>
	<head>
		<title>Products</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
	</head>
	<body>
		<h1>Products</h1>
		
		
		<hr>
		<h2>Catalogue</h2>
		<div>
			<table>
				<thead>
					<tr>
						<th>Product</th>
						<th>Description</th>
						<th>Price</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<#list products as product>
					<tr>
						<td>${product.name}</td>
						<td>
							<#if product.description??>
								${product.description}
							</#if>
						</td>

						<td>${product.prezzo}</td>
						<td>
							<a href="delete?id=${product.id}">Delete</a>
							<a href="?id=${product.id}">Edit</a>
						</td>
					</tr>
					</#list>
				</tbody>
			</table>
		</div>
	</body>
</html>