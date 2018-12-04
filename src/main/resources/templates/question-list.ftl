<!DOCTYPE html>
<html lang="en">

<!-- start head -->
<head>
	<title>All the questions</title>
	<link href="css/submit_question.css" rel="stylesheet" />
	<#include "head.ftl" />
</head>
<!-- end head -->

<body>
	<!-- navbar starts -->
	<#include "navbar.ftl" />
    <!-- navbar ends -->

    <div class="container">
    	<div class="content justify-center">
    		<div id="mainbar-question">
    			<div id="question" class="btm_border wmd-container">
    				<!-- context title-->
    				<div class="question-context-title btm_border">
		    			<div>All questions</div>

		    			<a href="/ask-question" float="right">Ask a question</a>
		    		</div>


		    		<!-- context title end-->

		    		<!-- question lists -->
		    		<#list questions as question>

		    		<div id="question-${question?counter}" class="answers">

		    				<div class="post-user">
	    						<img class="ml-2" src="img/person.png" >
								<span>${question[4]}<span>
	    					</div>
			    			<div class="question-title wmd-container border4-5893d4 margin-top5">

	    						<a href="/question-id=${question[0]}">${question[1]}</a>


	    					</div>
    				</div>
    				<#else>
    					<p>No Questions!</p>
    				</#list>


		    	</div>


    		</div>
    	</div>
    </div>
	<#include "footer.ftl" />
</body>
