/**
 * 
 */

jQuery.validator.addMethod(
		"greaterThan",
		function(value, element, params){
			return /Invalid/.test(new Date(value))
			|| /Invalid/.test(new Date($(params).val()))
			|| new Date(value) >= new Date($(params).val());
		},
		'Must be coherent with introduced date'
);

$("#addForm").validate({
	rules : {
		discontinuedDate : {
			greaterThan: "#introduced"
		}
	}
});

$("#editForm").validate({
	rules : {
		discontinuedDate : {
			greaterThan: "#introduced"
		}
	}
});