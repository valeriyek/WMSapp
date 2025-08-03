$(document).ready(function () {

            $("#uomTypeError").hide();
            $("#uomModelError").hide();
            $("#uomDescError").hide();


            var uomTypeError = false;
            var uomModelError = false;
            var uomDescError = false;


            function validate_uomType() {
                var val = $("#uomType").val();
                if (val == '') {
                    $("#uomTypeError").show();
                    $("#uomTypeError").html("Please select <b>Type</b>");
                    $("#uomTypeError").css('color','red');
                    uomTypeError = false;
                } else {
                    $("#uomTypeError").hide();
                    uomTypeError = true;
                }
                return uomTypeError;
            }

            function validate_uomModel() {
                var val = $("#uomModel").val();
                var exp = /^[A-Z\-\s]{4,12}$/;
                if (val == '') {
                    $("#uomModelError").show();
                    $("#uomModelError").html("Please Enter <b>Model</b>");
                    $("#uomModelError").css('color','red');
                    uomModelError = false;
                } else if (!exp.test(val)) {
                    $("#uomModelError").show();
                    $("#uomModelError").html("Only 4-12 chars allowed");
                    $("#uomModelError").css('color','red');
                    uomModelError = false;
                } else {

                    var id = 0;
                    if($("#id").val()!==undefined) {
						id = $("#id").val();
						uomModelError = true;
					}
                    $.ajax({
						url : 'validate',
						data: { "model" : val , "id" :id },
						success:function(resTxt) {
							if(resTxt!="") {
						 	    $("#uomModelError").show();
                   				$("#uomModelError").html(resTxt);
                   				$("#uomModelError").css('color','red');
                   				uomModelError = false;
							} else {
								$("#uomModelError").hide();
	                    		uomModelError = true;
							}
						}
					});

                }
                return uomModelError;
            }

            function validate_uomDesc() {
                var val = $("#uomDesc").val();
                var exp = /^[A-Za-z0-9\-\s\.\:\@\,]{10,100}$/;
                if (val == '') {
                    $("#uomDescError").show();
                    $("#uomDescError").html("Please Enter <b>Description</b>");
                    $("#uomDescError").css('color','red');
                    uomDescError = false;
                } else if (!exp.test(val)) {
                    $("#uomDescError").show();
                    $("#uomDescError").html("Only 10-100 chars allowed");
                    $("#uomDescError").css('color','red');
                    uomDescError = false;
                } else {
                    $("#uomDescError").hide();
                    uomDescError = true;
                }
                return uomDescError;
            }

            $("#uomType").change(function () {
                validate_uomType();
            });

            $("#uomModel").keyup(function () {
                $(this).val($(this).val().toUpperCase());
                validate_uomModel();
            });

            $("#uomDesc").keyup(function () {
                validate_uomDesc();
            });

            $("#myUomForm").submit(function () {
                validate_uomType();
                validate_uomModel();
                validate_uomDesc();

                if (uomTypeError && uomModelError && uomDescError)
                    return true;
                else
                    return false;
            });
        });