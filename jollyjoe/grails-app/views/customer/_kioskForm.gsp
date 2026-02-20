<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<a href="/">
				<img src="${resource(dir:'images',file:'jolly_logo.png')}" alt="Jolly Joe logo" />
			</a>
		</div>
	</div>
	
	<div class="row"> <!-- Begin First Row -->
		<div class="col-sm-5 col-sm-offset-1" style="color: white;"> <!-- Welcome Column -->
			<h4 style="font-family: 'Segoe Script', cursive; font-size: 2em; letter-spacing: 1px;">${welcome}</h4>
			<p><h4 style="font-family: 'Segoe Script', cursive; font-size: 1.7em; letter-spacing: 1px;">${points}</h4></p>
		</div>
		
		<div class="col-sm-4 col-sm-offset-1" style="max-width: 350px;"> <!-- Begin Right Column, narrower -->
			<g:textField name="phone" class="form-control" style="max-width: 300px; margin: 0 auto;"
				placeholder="Enter your cell number to check in" value="${customer?.phone}" />
			<div class="row"> <!-- First Button Row Spacer -->
				<h3></h3>
			</div>
			<div class="row g-0" style="max-width: 300px; margin: 0 auto;"> <!-- First Button Row -->
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="1" onclick="padkey(this.value)"/>
				</div>
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="2" onclick="padkey(this.value)"/>
				</div>
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="3" onclick="padkey(this.value)"/>
				</div>
			</div>
			
			<div class="row g-0"> <!-- Second Button Row Spacer -->
				<h3></h3>
			</div>
			<div class="row g-0" style="max-width: 300px; margin: 0 auto;"> <!-- Second Button Row -->
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="4" onclick="padkey(this.value)"/>
				</div>
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="5" onclick="padkey(this.value)"/>
				</div>
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="6" onclick="padkey(this.value)"/>
				</div>
			</div>
			
			<div class="row g-0"> <!-- Third Button Row Spacer -->
				<h3></h3>
			</div>
			<div class="row g-0" style="max-width: 300px; margin: 0 auto;"> <!-- Third Button Row -->
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="7" onclick="padkey(this.value)"/>
				</div>
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="8" onclick="padkey(this.value)"/>
				</div>
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="9" onclick="padkey(this.value)"/>
				</div>
			</div>
			
			<div class="row g-0"> <!-- Fourth Button Row Spacer -->
				<h3></h3>
			</div>
			<div class="row g-0" style="max-width: 300px; margin: 0 auto;"> <!-- Fourth Button Row -->
				   <div class="col-sm-4">
					   <input class="btn btn-danger btn-lg btn-block" type="button" name="pad" value="Del" onclick="delkey()" />
				</div>
				<div class="col-sm-4">
					<input class="btn btn-primary btn-lg btn-block" type="button" name="pad" value="0" onclick="padkey(this.value)"/>
				</div>
				<div class="col-sm-4">
					<g:submitButton class="btn btn-success btn-lg btn-block" name="pad" value="Go" />
				</div>
			</div>
		
		</div> <!-- End Right Column -->
	</div> <!-- End First Row -->
</div> <!-- End Container -->

<!-- pad button script -->
<script>
function padkey(num) {
	var txt = document.getElementById("phone").value;
	txt = txt + num;
	document.getElementById("phone").value = txt;
}

function delkey() {
	var input = document.getElementById("phone");
	var txt = input.value;
	// Remove last character
	input.value = txt.slice(0, -1);
}
</script>
