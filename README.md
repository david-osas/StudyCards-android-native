# StudyCards-android-native

Welcome to the android native code for StudyCards, an application created to help people study. Users can create decks filled with cards and put a question and an answer in each card. They can later view any selected deck and quiz themselves on saved cards, thus helping them in their learning.
Technologies uesd: Java, android studio and Room ORM

# Application

Starting from the home screen a user has the choice to view saved decks or create a new deck

![home](images/home.jpg?raw=true)

If create new deck is clicked, the user is brought to a different screen to input the title of the deck and create as many cards in that deck as desired. After creating the desired amount of cards the user can click on submit deck to save the new deck and would then be redirected to the home screen

![create deck](images/create_deck.jpg?raw=true)

If view decks is clicked, the user is brought to a new screen that shows a list of saved decks and the number of cards in each deck

![decks](images/decks.jpg?raw=true)

Clicking on a deck item brings the user to a screen showing the list of cards within the selected deck and clicking the floating action button at the bottom right brings a new screen allowing the user to directly add a new card to the currently viewed deck, after which the user is redirected back to the cards list

![cards](images/cards.jpg?raw=true)


![add card](images/add_card.jpg?raw=true)

Long pressing a list item allows the user to delete the selected item

![delete](images/delete.jpg?raw=true)

Clicking on a card item directs the user to a new screen showing the question stored in the card and clicking the reveal answer button shows the answer saved with the question

![check question](images/check_question.jpg?raw=true)


![check answer](images/check_answer.jpg?raw=true)

Lastly, by clicking on the icon in the appbar the user can initiate study mode which sends daily notifications reminding the user to study

![study mode](images/study_mode.jpg?raw=true)


