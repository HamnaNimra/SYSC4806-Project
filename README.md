# SYSC4806-Project

## Milestone 1: 

Early prototype. Give a 10-15 minute demo during the lab on March 12th.
For this milestone we are looking to see enough functionality to get a feel for the system and how it will
work. One important use case should be operational. It should collect data from the back end, do
something with it and display the result. The display doesn't need to be fancy. There should be a GitHub
repo, integrated with Travis CI (or other hosted CI), and the app should be up and running on Heroku.
We will also inspect the README file, the Issues, the Kanban, the code reviews, the tests, and we will
verify that everybody is participating in all aspects of the project (if that is not the case, different team
members will end up with different grades).

# Project topics:
Details on the marking scheme and the various milestones and deadlines will be made available in due time and in a separate document. In the meantime, please pick one of the projects below. Feel free to ask the instructor any questions that can help clarify the topic, ideally during lab time or office hours. For emails, please pick a team rep to ask the questions to avoid duplicates and possible confusion and misunderstandings.


## 1- Perk manager

The User can create a Profile with all the memberships and cards she has: Air Miles, CAA, credit card etc. A "Perk" is some kind of discount for a given membership and a given product: 10% off movies with a Visa card, free domestic flight with 10,000 Air Miles, etc. Perks may also have limitations in geographic area and in time. These perks can be posted anytime by any user (the user may first need to enter the product or membership for which the perk applies). Users can upvote or downvote these perks depending on whether they find them useful or incorrect. Search results for perks can be listed in decreasing amount of votes, or by expiry date. Logged-in users can search for products for which there are perks available matching their profile. A user without a profile (or not logged-in) can still search for perks, but won't get the benefit of finding matches for their memberships.

## 2- Trustworthy product reviews

Users review Products. These products could be identified by a link to the web site where they are listed. A review consists of a star-rating and some text. Users can also "follow" other users whose reviews they find valuable. A user can then list products for a given category by average rating, or the average rating of only the users they follow. A user can also list the users whose ratings are the most "similar" to their own using the Jaccard distance (google it!). Product reviews can also be ranked according to the similarity score of the people who reviewed them. Users can also list the most followed users. The degree of separation (see Kevin Bacon!) according to the "follow" link can also be displayed next to each reviewer (the assumption is that the "closer" a user is to you, the more trustworthy he/she should be to you).

## 3- Amazin online bookstore

Bookstore Owner can upload and edit Book information (ISBN, picture, description, author, publisher,...) and inventory. User can search for, and browse through, the books in the bookstore, sort/filter them based on the above information. User can then decide to purchase one or many books by putting them in the Shopping Cart and proceeding to Checkout. The purchase itself will obviously be simulated, but purchases cannot exceed the inventory. User can also view Book Recommendations based on past purchases. This is done by looking for users whose purchases are most similar (using Jaccard distance: Google it!), and then recommending books purchased by those similar users but that the current User hasn't yet purchased.

## 4- Mini-Shopify

Merchant can create a new shop by filling in a form containing: the name of the shop, the categories/tags relevant to the shop, and other fields that are up to you. The Merchant can upload products to populate the shop along with a description, picture, and inventory number. Customers can find a shop by looking up the name of the shop or searching by category/tag. Once they find the shop the want, they can browse through the product catalog of the shop, and can then decide to purchase one or many products by putting them in the Shopping Cart and proceeding to Checkout. The purchase itself will obviously be simulated, but purchases cannot exceed the inventory.

## 5- Mini-SurveyMonkey

Surveyor can create a survey with a list of Questions. Questions can be open-ended (text), asking for a number within a range, or asking to choose among many options.  Users fill out a survey that is a form generated based on the type of questions in the survey. Surveyor can close the survey whenever they want (thus not letting in new users to fill out the survey), and at that point a survey result is generated, compiling the answers: for open-ended questions, the answers are just listed as-is, for number questions a histogram of the answers is generated, for choice questions a pie chart is generated

## 6- COVID Contract Tracing Questionnaire

Contact tracing personnel have to frequently call Contact Cases, i.e., people who were in contact with someone known to have COVID, to find out whether they are experiencing symptoms and whether they need any support while they are in quarantine. We want to relieve their work by providing a questionnaire that the Contact Cases can fill out themselves. The Public Health Unit is responsible for creating the case in the system, providing the name and phone number/email of the Contact Case, along with private information (NOT to be made visible to the Contact Case!) such as the Case ID that the Contact Case is related to, and the Exposure Date. The Contact Case will then receive a daily reminder (by phone or email) to fill out a questionnaire, one per day for X days, asking questions such as: "since yesterday's questionnaire, have you remained inside you residence?", "do you need any support to adhere to self-isolation?", (if yes) "what type of support do you need? (food/groceries, medications/prescriptions, pet care), "are you experiencing any symptoms? (cough, fever, runny nose)".

The system should then flag cases where the questionnaire was either not filled out or the Contact Case declared having experienced symptoms (in which case a human will call the Contact Case) or the Contact Case asked for support (in which case the appropriate support system is referred to).

## 7- Graduate Admissions Management System

Applicants submit their file containing their personal info, their desired field of research, a list of profs they want to work with, and various documents such as their CV, a copy of their diploma, grade audit, by a certain deadline. Profs submit a profile specifying their fields of research. The applicant files can be filtered/selected by an administrator. Those that are considered good enough are routed to profs for evaluation by a deadline. Profs receive an email notification to go check the list of new applicants assigned to them. They evaluate the student by studying their application info, and enter their assessment: “don’t recommend for admission”, “recommend but not interested in supervision”, “recommend but no funding”, “recommend and yes to funding”. The admin can then view the recommendation of the profs and make a decision.
