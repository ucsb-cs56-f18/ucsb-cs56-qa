<!DOCTYPE html>

<!-- Sample function to write data to Firebase -->
<#include "head.ftl" />
<script type="text/javascript">
	// var database = firebase.database();

	function writeSampleDataSegment(word, number, sentence) {
		firebase.database().ref('sampleDataSegment/' + word).set({
			number: number,
			sentence: sentence
		});
	}
</script>

