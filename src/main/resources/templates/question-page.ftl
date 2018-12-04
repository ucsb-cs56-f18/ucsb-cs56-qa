<!DOCTYPE html>
<html lang="en">

<!-- start head -->
<head>
	<title>Submit an Answer</title>
	<link href="css/submit_question.css" rel="stylesheet" />
	<#include "head.ftl" />
</head>
<!-- end head -->

<body class="body-style">
	<!-- navbar starts -->
	<#include "navbar.ftl" />
    <!-- navbar ends -->

    <div class="container">
    	<div class="content justify-center">
    		<div id="mainbar-question">

    			<!-- Here is the question section -->

                <div id="question" class="btm_border wmd-container">
    				<div class="post-layout">
	    				<!-- Here is the question title -->
		    			<div class="question-context-title btm_border">
		    			<div>${question[1]}</div>

                        <a href="/question-list" float="right">View all the questions</a>
		    			</div>
		    			<!-- Here is the question body -->
		    			<div class="post-text wmd-container border4-ffc20e margin-top5">


		    				<p>
		    					${question[2]}
		    				</p>

		    			</div>
    				</div>
    			</div>
    			<!-- the question section ends-->

    			<!-- Here is the answer section -->
    			<div id="answers-sec" class="btm_border wmd-container margin-top">

    				<!-- Here are all the answers -->

    				<label id="answer-header" class="s-label mb4" for="input-answer">${answers?size} Answer</label>

                    <#list answers as answer>
    				<div id="answer-${answer?counter}" class="answers">
    					<div class="post-layout wmd-container">


    						<div class="post-text wmd-container border4-5893d4 margin-top5">
    							<div class="post-user">
                                    <img class="ml-2" src="img/person.png" >
                                <span>${answerer[answer?index]}&nbsp;</span>
                                <span>&nbsp;${answer[4]}</span>

    							</div>
    							<p>
    								${answer[2]}
    							</p>
    						</div>
    					</div>
    				</div>
                    </#list>


    				<!--the answers end-->

    				<!-- the answer form section -->
	    			<form id="submit_form" action="/new_answer_qid=${question[0]}" method="post">

					<div id="post-editor" class="margin-top" style="position: relative;">
						<label class="s-label mb4" for="input-answer">Your Answer</label>
						<div class="wmd-container">
						<textarea id="input-answer" class="s-input-message" name="content" style="opacity: 1; height: 238px;">
						</textarea>
						</div>
					</div>

					<div id="post-uid" class="margin-top" style="position: relative;">
						<label class="s-label mb4" for="input-answer">Your UserID</label>
						<div class="wmd-container">
						<textarea id="input-uid" class="s-input-message" name="answererid"style="opacity: 1; height: 38px;">
						</textarea>
						</div>
					</div>

					<div class="wmd-container">
						<button id="answer-button" class="s-btn" type="submit" tabindex="120" autocomplete="off">
		                 Submit Your Answer
		                </button>
	                </div>

					</form>
					<!-- the answer form section ends-->

	    		</div>
    			<!-- the answer section ends-->

    		</div>
    	</div>
    </div>
	<#include "footer.ftl" />
</body>
