/*
 * Licensed Materials - Property of IBM
 * 5737-I23
 * Copyright IBM Corp. 2018 - 2021. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
*/


$(document).ready(
		function() {
			hide_panels();
			default_initialize();
			// birthDate input
		      var date_input=$('input[name="birthDate"]'); 
		      var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
		      var options={
		        format: 'mm/dd/yyyy',
		        container: container,
		        todayHighlight: true,
		        autoclose: true,
		      };
		      date_input.datepicker(options);
			// startDate input
		      var startDate_input=$('input[name="startDate"]'); 
		      var startContainer=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
		      var startOptions={
		        format: 'mm/dd/yyyy',
		        container: startContainer,
		        todayHighlight: true,
		        autoclose: true,
		      };
		      startDate_input.datepicker(startOptions);
				// bankruptcyDate input
		      var bankruptcyDate_input=$('input[name="bankruptcyDate"]'); 
		      var bankruptcyContainer=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
		      var bankruptcyOptions={
		        format: 'mm/dd/yyyy',
		        container: bankruptcyContainer,
		        todayHighlight: true,
		        autoclose: true,
		      };
		      bankruptcyDate_input.datepicker(bankruptcyOptions);

			// process the form
			$('form').submit(
					function(event) {
						// stop the form from submitting the normal way and
						// refreshing the page
						event.preventDefault();
						hide_panels();
						if ($("#trace").is(':checked')) {
							showTrace = true
						} else {
							showTrace = false
						}
						var formData = {
								'firstName' : $('input[name=firstName]').val(),
								'lastName' : $('input[name=lastName]').val(),
								'birthDate' : $('input[name=birthDate]').val(),
								'bankruptcyDate' : $('input[name=bankruptcyDate]').val(),
								'bankruptcyReason' : $('input[name=bankruptcyReason]').val(),
								'bankruptcyChapter' : parseInt($('input[name=bankruptcyChapter]').val()),
								'startDate' : $('input[name=startDate]').val(),
								'SSNCode' : $('input[name=SSNCode]').val(),
								'zipCode' : $('input[name=zipCode]').val(),
							'server' : $('input[name=server]').val(),
							'decisionId' : $('input[name=decisionId]').val(),
							'spaceId' : $('input[name=spaceId]').val(),
							'operation' : $('input[name=operation]').val(),
							'user' : $('input[name=user]').val(),
							'password' : $('input[name=password]').val(),
							'instanceType' : $('input[name=instanceType]:checked', '#instanceChoice').val(),
							'yearly-income' : parseInt($('input[name=yearly-income]').val()),
							'credit-score' : parseInt($('input[name=credit-score]').val()),
							'amount' : parseInt($('input[name=amount]').val()),
							'duration' : parseInt($('input[name=duration]').val()),
							'yearly-interest-rate' : parseFloat($('input[name=yearly-interest-rate]').val()),
							'showTrace' : showTrace
						};
						// update swagger link
						$("a.swaggerLink").attr("href", "https://" + $('input[name=server]').val() + "/ads/runtime/api/swagger-ui/");

						// process the form
						var request = $.ajax({
							type : 'POST',
							url : 'validate',
							data : JSON.stringify(formData),
							dataType : 'json',
						});

						request.done(function(data) {
							console.log(data);
							show_panels();
							if (data.error) {
							    $('#info-panel').attr('class','panel panel-danger');
                            	$('#info-header').text('Error')
                            	$('#info-json-panel').attr('class','panel panel-danger');
                            	$('#info-json-header').text('Error')
                            	$('#info-text').text(data.text)
                            	$('#info-json-text').text(data.text)
							} else if (data.notFound == true) {
                                $('#info-panel').attr('class','panel panel-danger');
                                $('#info-header').text('Decision not found')
                                $('#info-json-panel').attr('class','panel panel-danger');
                                $('#info-json-header').text('Decision not found')
                                $('#info-text').text(data.message)
                                $('#info-json-text').text(data.message)
                            } else if (data.validData == false) {
                                 $('#info-panel').attr('class','panel panel-danger');
                                 $('#info-header').text('Invalid input data')
                                 $('#info-json-panel').attr('class','panel panel-danger');
                                 $('#info-json-header').text('Invalid input data')
                                 $('#info-text').text(data.message)
                                 $('#info-json-text').text(data.message)
                             } else if (data.success == true) {
								if (data.approved == true) {
                                 	$('#info-panel').attr('class','panel panel-success');
                                 	$('#info-header').text('Approved loan request')
                                 	$('#info-json-panel').attr('class','panel panel-success');
                                 	$('#info-json-header').text('Approved loan request')
                                } else {
                                 	$('#info-panel').attr('class','panel panel-warning');
                                 	$('#info-header').text('Rejected loan request')
                                    $('#info-json-panel').attr('class','panel panel-warning');
                                 	$('#info-json-header').text('Rejected loan request')
                                }
							    $('#info-json-text').html(format_json(data.jsonOutputContent))
								if (data.showTrace)
									$('#info-text').html(data.trace)
								else
									$('#info-text').text(data.message)
								}
						});

						request.fail(function(jqXHR, textStatus) {
							console.log("Request failed: " + textStatus);
							$('#info-panel').attr('class','panel panel-error');
							$('#info-header').text('Error')
							$('#info-text').text(textStatus)
						});

					});

		});

function format_lines(messages) {
	var t = messages.map(function(l) {
		return l.line;
	});
	return t.join("</br>");
}

function format_json(obj) {
	var str = JSON.stringify(obj, null, 4 );
	return  "<pre><code>" + str + "<\pre><\code>";
}

function show_panels() {
	$('#response').css('visibility', 'visible');
	$('#info-panel').css('visibility', 'visible');
	$('#info-json-panel').css('visibility', 'visible');
}

function hide_panels() {
  $('#response').css('visibility', 'hidden');
  $('#info-panel').css('visibility', 'hidden');
  $('#info-json-panel').css('visibility', 'hidden');
}

function default_initialize() {
	$('input[name="user"]').val(ADSUSERNAME);
	$('input[name="password"]').val(ADSPASSWORD);
	$('input[name="server"]').val(SERVERNAME);
	$('input[name="decisionId"]').val(DECISIONID);
	$('input[name="spaceId"]').val(SPACEID);
	$("a.swaggerLink").attr("href", "https://" + SERVERNAME + "/ads/runtime/api/swagger-ui/");
}
