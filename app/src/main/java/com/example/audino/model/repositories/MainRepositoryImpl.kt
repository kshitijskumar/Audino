package com.example.audino.model.repositories

import android.util.Log
import com.example.audino.model.api.ApiService
import com.example.audino.model.response.AllBooksResponse
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.BookSummaryResponse
import com.example.audino.model.response.GenreResponse
import com.example.audino.utils.Injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.logging.Logger

class MainRepositoryImpl(
    private val api: ApiService = Injector.getInjector().provideApiService()
) : MainRepository {

    companion object {
        val bookIdAndBookResponseMap = mutableMapOf<String, BookResponse>()
        val bookIdAndSummaryText = mutableMapOf<String, String>()
    }

    override suspend fun getAllBooks(): AllBooksResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.fetchAllBooks()
                if (response.isSuccessful && response.body() != null) {
                    Log.d("DeeplinkStuff", "caching start")
                    cacheAllBooksResponse(response.body()!!)
                    Log.d("DeeplinkStuff", "$bookIdAndBookResponseMap")
                    Log.d("DeeplinkStuff", "sending")
                    response.body()!!
                } else {
                    AllBooksResponse()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                AllBooksResponse()
            }
        }
    }

    private suspend fun cacheAllBooksResponse(allBooksResponse: AllBooksResponse) {
        withContext(Dispatchers.Default) {
            allBooksResponse.genres.forEach { genre ->
                genre.books.forEach { book ->
                    bookIdAndBookResponseMap[book.bookId ?: ""] = book
                }
            }
        }
    }

//    override suspend fun getBookSummaryContent(bookId: String): BookSummaryResponse {
//        //returning dummy for now
//        withContext(Dispatchers.IO) {
//            val sim = api.fetchSummary()
//            if (sim.isSuccessful && sim.body() != null) {
//                Log.d("SummaryStuff", "${sim.body()}")
//            }
//        }
//        return BookSummaryResponse(
//            bookId,
//            bookIdAndBookResponseMap[bookId]?.bookName ?: "Some book",
//            bookIdAndBookResponseMap[bookId]?.authorName ?: "Some author",
//            "CHAPTER I. Down the Rabbit-Hole\n" +
//                    "\n" +
//                    "Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do: once or twice she had peeped into the book her sister was reading, but it had no pictures or conversations in it, “and what is the use of a book,” thought Alice “without pictures or conversations?”  So she was considering in her own mind (as well as she could, for the hot day made her feel very sleepy and stupid), whether the pleasure of making a daisy-chain would be worth the trouble of getting up and picking the daisies, when suddenly a White Rabbit with pink eyes ran close by her.\n" +
//                    "I shall be late!” (when she thought it over afterwards, it occurred to her that she ought to have wondered at this, but at the time it all seemed quite natural); but when the Rabbit actually _took a watch out of its waistcoat-pocket_, and looked at it, and then hurried on, Alice started to her feet, for it flashed across her mind that she had never before seen a rabbit with either a waistcoat-pocket, or a watch to take out of it, and burning with curiosity, she ran across the field after it, and fortunately was just in time to see it pop down a large rabbit-hole under the hedge.\n" +
//                    "The rabbit-hole went straight on like a tunnel for some way, and then dipped suddenly down, so suddenly that Alice had not a moment to think about stopping herself before she found herself falling down a very deep well.\n" +
//                    "There seemed to be no use in waiting by the little door, so she went back to the table, half hoping she might find another key on it, or at any rate a book of rules for shutting people up like telescopes: this time she found a little bottle on it, (“which certainly was not here before,” said Alice,) and round the neck of the bottle was a paper label, with the words “DRINK ME,” beautifully printed on it in large letters.\n" +
//                    "*      *      *      *      *      *      *      *      *      *      *      *      *  *      *      *      *      *      *      *   “What a curious feeling!” said Alice; “I must be shutting up like a telescope.”  And so it was indeed: she was now only ten inches high, and her face brightened up at the thought that she was now the right size for going through the little door into that lovely garden.\n" +
//                    "\n" +
//                    "CHAPTER II. The Pool of Tears\n" +
//                    "\n" +
//                    "Oh dear, what nonsense I’m talking!”  Just then her head struck against the roof of the hall: in fact she was now more than nine feet high, and she at once took up the little golden key and hurried off to the garden door.\n" +
//                    "“How cheerfully he seems to grin,     How neatly spread his claws, And welcome little fishes in     With gently smiling jaws!”   “I’m sure those are not the right words,” said poor Alice, and her eyes filled with tears again as she went on, “I must be Mabel after all, and I shall have to go and live in that poky little house, and have next to no toys to play with, and oh!\n" +
//                    "Tell me that first, and then, if I like being that person, I’ll come up: if not, I’ll stay down here till I’m somebody else’—but, oh dear!” cried Alice, with a sudden burst of tears, “I do wish they _would_ put their heads down!\n" +
//                    "However, everything is queer to-day.”  Just then she heard something splashing about in the pool a little way off, and she swam nearer to make out what it was: at first she thought it must be a walrus or hippopotamus, but then she remembered how small she was now, and she soon made out that it was only a mouse that had slipped in like herself.\n" +
//                    "Everything is so out-of-the-way down here, that I should think very likely it can talk: at any rate, there’s no harm in trying.” So she began: “O Mouse, do you know the way out of this pool?\n" +
//                    "He says it kills all the rats and—oh dear!” cried Alice in a sorrowful tone, “I’m afraid I’ve offended it again!” For the Mouse was swimming away from her as hard as it could go, and making quite a commotion in the pool as it went.\n" +
//                    "Do come back again, and we won’t talk about cats or dogs either, if you don’t like them!” When the Mouse heard this, it turned round and swam slowly back to her: its face was quite pale (with passion, Alice thought), and it said in a low trembling voice, “Let us get to the shore, and then I’ll tell you my history, and you’ll understand why it is I hate cats and dogs.”  It was high time to go, for the pool was getting quite crowded with the birds and animals that had fallen into it: there were a Duck and a Dodo, a Lory and an Eaglet, and several other curious creatures.\n" +
//                    "\n" +
//                    "CHAPTER III. A Caucus-Race and a Long Tale\n" +
//                    "\n" +
//                    "“Found _it_,” the Mouse replied rather crossly: “of course you know what ‘it’ means.”  “I know what ‘it’ means well enough, when _I_ find a thing,” said the Duck: “it’s generally a frog or a worm.\n" +
//                    "“What I was going to say,” said the Dodo in an offended tone, “was, that the best thing to get us dry would be a Caucus-race.”  “What _is_ a Caucus-race?” said Alice; not that she wanted much to know, but the Dodo had paused as if it thought that _somebody_ ought to speak, and no one else seemed inclined to say anything.\n" +
//                    "However, when they had been running half an hour or so, and were quite dry again, the Dodo suddenly called out “The race is over!” and they all crowded round it, panting, and asking, “But who has won?”  This question the Dodo could not answer without a great deal of thought, and it sat for a long time with one finger pressed upon its forehead (the position in which you usually see Shakespeare, in the pictures of him), while the rest waited in silence.\n" +
//                    "“Why, _she_, of course,” said the Dodo, pointing to Alice with one finger; and the whole party at once crowded round her, calling out in a confused way, “Prizes!\n" +
//                    "“You promised to tell me your history, you know,” said Alice, “and why it is you hate—C and D,” she added in a whisper, half afraid that it would be offended again.\n" +
//                    "“Mine is a long and a sad tale!” said the Mouse, turning to Alice, and sighing.\n" +
//                    "\n" +
//                    "CHAPTER IV. The Rabbit Sends in a Little Bill\n" +
//                    "\n" +
//                    "Where _can_ I have dropped them, I wonder?” Alice guessed in a moment that it was looking for the fan and the pair of white kid gloves, and she very good-naturedly began hunting about for them, but they were nowhere to be seen—everything seemed to have changed since her swim in the pool, and the great hall, with the glass table and the little door, had vanished completely.\n" +
//                    "But I’ve got to see that the mouse doesn’t get out.’ Only I don’t think,” Alice went on, “that they’d let Dinah stop in the house if it began ordering people about like that!”  By this time she had found her way into a tidy little room with a table in the window, and on it (as she had hoped) a fan and two or three pairs of tiny white kid gloves: she took up the fan and a pair of the gloves, and was just going to leave the room, when her eye fell upon a little bottle that stood near the looking-glass.\n" +
//                    "I do hope it’ll make me grow large again, for really I’m quite tired of being such a tiny little thing!”  It did so indeed, and much sooner than she had expected: before she had drunk half the bottle, she found her head pressing against the ceiling, and had to stoop to save her neck from being broken.\n" +
//                    "As for pulling me out of the window, I only wish they _could!_ I’m sure _I_ don’t want to stay in here any longer!”  She waited for some time without hearing anything more: at last came a rumbling of little cartwheels, and the sound of a good many voices all talking together: she made out the words: “Where’s the other ladder?—Why, I hadn’t to bring but one; Bill’s got the other—Bill!\n" +
//                    "Tell us all about it!”  Last came a little feeble, squeaking voice, (“That’s Bill,” thought Alice,) “Well, I hardly know—No more, thank ye; I’m better now—but I’m a deal too flustered to tell you—all I know is, something comes at me like a Jack-in-the-box, and up I goes like a sky-rocket!”  “So you did, old fellow!” said the others.\n" +
//                    "If they had any sense, they’d take the roof off.” After a minute or two, they began moving about again, and Alice heard the Rabbit say, “A barrowful will do, to begin with.”  “A barrowful of _what?_” thought Alice; but she had not long to doubt, for the next moment a shower of little pebbles came rattling in at the window, and some of them hit her in the face.\n" +
//                    "“Poor little thing!” said Alice, in a coaxing tone, and she tried hard to whistle to it; but she was terribly frightened all the time at the thought that it might be hungry, in which case it would be very likely to eat her up in spite of all her coaxing.\n" +
//                    "“And yet what a dear little puppy it was!” said Alice, as she leant against a buttercup to rest herself, and fanned herself with one of the leaves: “I should have liked teaching it tricks very much, if—if I’d only been the right size to do it!\n" +
//                    "\n" +
//                    "CHAPTER V. Advice from a Caterpillar\n" +
//                    "\n" +
//                    "Alice replied, rather shyly, “I—I hardly know, sir, just at present—at least I know who I _was_ when I got up this morning, but I think I must have been changed several times since then.”  “What do you mean by that?” said the Caterpillar sternly.\n" +
//                    "“I’m afraid I can’t put it more clearly,” Alice replied very politely, “for I can’t understand it myself to begin with; and being so many different sizes in a day is very confusing.”  “It isn’t,” said the Caterpillar.\n" +
//                    "“Well, perhaps you haven’t found it so yet,” said Alice; “but when you have to turn into a chrysalis—you will some day, you know—and then after that into a butterfly, I should think you’ll feel it a little queer, won’t you?”  “Not a bit,” said the Caterpillar.\n" +
//                    "For some minutes it puffed away without speaking, but at last it unfolded its arms, took the hookah out of its mouth again, and said, “So you think you’re changed, do you?”  “I’m afraid I am, sir,” said Alice; “I can’t remember things as I used—and I don’t keep the same size for ten minutes together!”  “Can’t remember _what_ things?” said the Caterpillar.\n" +
//                    "Alice folded her hands, and began:—  “You are old, Father William,” the young man said,     “And your hair has become very white; And yet you incessantly stand on your head—     Do you think, at your age, it is right?”  “In my youth,” Father William replied to his son,     “I feared it might injure the brain; But, now that I’m perfectly sure I have none,     Why, I do it again and again.”  “You are old,” said the youth, “as I mentioned before,     And have grown most uncommonly fat; Yet you turned a back-somersault in at the door—     Pray, what is the reason of that?”  “In my youth,” said the sage, as he shook his grey locks,     “I kept all my limbs very supple By the use of this ointment—one shilling the box—     Allow me to sell you a couple?”  “You are old,” said the youth, “and your jaws are too weak     For anything tougher than suet; Yet you finished the goose, with the bones and the beak—     Pray, how did you manage to do it?”  “In my youth,” said his father, “I took to the law,     And argued each case with my wife; And the muscular strength, which it gave to my jaw,     Has lasted the rest of my life.”  “You are old,” said the youth, “one would hardly suppose     That your eye was as steady as ever; Yet you balanced an eel on the end of your nose—     What made you so awfully clever?”  “I have answered three questions, and that is enough,”     Said his father; “don’t give yourself airs!\n" +
//                    "“Not _quite_ right, I’m afraid,” said Alice, timidly; “some of the words have got altered.”  “It is wrong from beginning to end,” said the Caterpillar decidedly, and there was silence for some minutes.\n" +
//                    "*      *      *      *      *      *      *      *      *      *      *      *      *  *      *      *      *      *      *      *   “Come, my head’s free at last!” said Alice in a tone of delight, which changed into alarm in another moment, when she found that her shoulders were nowhere to be found: all she could see, when she looked down, was an immense length of neck, which seemed to rise like a stalk out of a sea of green leaves that lay far below her.\n" +
//                    "\n" +
//                    "CHAPTER VI. Pig and Pepper\n" +
//                    "\n" +
//                    "“Please, then,” said Alice, “how am I to get in?”  “There might be some sense in your knocking,” the Footman went on without attending to her, “if we had the door between us.\n" +
//                    "“Please would you tell me,” said Alice, a little timidly, for she was not quite sure whether it was good manners for her to speak first, “why your cat grins like that?”  “It’s a Cheshire cat,” said the Duchess, “and that’s why.\n" +
//                    "Pig!”  She said the last word with such sudden violence that Alice quite jumped; but she saw in another moment that it was addressed to the baby, and not to her, so she took courage, and went on again:—  “I didn’t know that Cheshire cats always grinned; in fact, I didn’t know that cats _could_ grin.”  “They all can,” said the Duchess; “and most of ’em do.”  “I don’t know of any that do,” Alice said very politely, feeling quite pleased to have got into a conversation.\n" +
//                    "“You don’t know much,” said the Duchess; “and that’s a fact.”  Alice did not at all like the tone of this remark, and thought it would be as well to introduce some other subject of conversation.\n" +
//                    "I—”  “Oh, don’t bother _me_,” said the Duchess; “I never could abide figures!” And with that she began nursing her child again, singing a sort of lullaby to it as she did so, and giving it a violent shake at the end of every line:  “Speak roughly to your little boy,     And beat him when he sneezes: He only does it to annoy,     Because he knows it teases.”   CHORUS.\n" +
//                    "wow!”   While the Duchess sang the second verse of the song, she kept tossing the baby violently up and down, and the poor little thing howled so, that Alice could hardly hear the words:—  “I speak severely to my boy,     I beat him when he sneezes; For he can thoroughly enjoy     The pepper when he pleases!”   CHORUS.\n" +
//                    "“If I don’t take this child away with me,” thought Alice, “they’re sure to kill it in a day or two: wouldn’t it be murder to leave it behind?” She said the last words out loud, and the little thing grunted in reply (it had left off sneezing by this time).\n" +
//                    "“Oh, you’re sure to do that,” said the Cat, “if you only walk long enough.”  Alice felt that this could not be denied, so she tried another question.\n" +
//                    "“Do you play croquet with the Queen to-day?”  “I should like it very much,” said Alice, “but I haven’t been invited yet.”  “You’ll see me there,” said the Cat, and vanished.\n" +
//                    "\n" +
//                    "CHAPTER VII. A Mad Tea-Party\n" +
//                    "\n" +
//                    "There was a table set out under a tree in front of the house, and the March Hare and the Hatter were having tea at it: a Dormouse was sitting between them, fast asleep, and the other two were using it as a cushion, resting their elbows on it, and talking over its head.\n" +
//                    "“You should learn not to make personal remarks,” Alice said with some severity; “it’s very rude.”  The Hatter opened his eyes very wide on hearing this; but all he _said_ was, “Why is a raven like a writing-desk?”  “Come, we shall have some fun now!” thought Alice.\n" +
//                    "“You might just as well say that ‘I see what I eat’ is the same thing as ‘I eat what I see’!”  “You might just as well say,” added the March Hare, “that ‘I like what I get’ is the same thing as ‘I get what I like’!”  “You might just as well say,” added the Dormouse, who seemed to be talking in his sleep, “that ‘I breathe when I sleep’ is the same thing as ‘I sleep when I breathe’!”  “It _is_ the same thing with you,” said the Hatter, and here the conversation dropped, and the party sat silent for a minute, while Alice thought over all she could remember about ravens and writing-desks, which wasn’t much.\n" +
//                    "“Yes, but some crumbs must have got in as well,” the Hatter grumbled: “you shouldn’t have put it in with the bread-knife.”  The March Hare took the watch and looked at it gloomily: then he dipped it into his cup of tea, and looked at it again: but he could think of nothing better to say than his first remark, “It was the _best_ butter, you know.”  Alice had been looking over his shoulder with some curiosity.\n" +
//                    "“Does _your_ watch tell you what year it is?”  “Of course not,” Alice replied very readily: “but that’s because it stays the same year for such a long time together.”  “Which is just the case with _mine_,” said the Hatter.\n" +
//                    "The Dormouse shook its head impatiently, and said, without opening its eyes, “Of course, of course; just what I was going to remark myself.”  “Have you guessed the riddle yet?” the Hatter said, turning to Alice again.\n" +
//                    "Half-past one, time for dinner!”  (“I only wish it was,” the March Hare said to itself in a whisper.)  “That would be grand, certainly,” said Alice thoughtfully: “but then—I shouldn’t be hungry for it, you know.”  “Not at first, perhaps,” said the Hatter: “but you could keep it to half-past one as long as you liked.”  “Is that the way _you_ manage?” Alice asked.\n" +
//                    "“They couldn’t have done that, you know,” Alice gently remarked; “they’d have been ill.”  “So they were,” said the Dormouse; “_very_ ill.”  Alice tried to fancy to herself what such an extraordinary ways of living would be like, but it puzzled her too much, so she went on: “But why did they live at the bottom of a well?”  “Take some more tea,” the March Hare said to Alice, very earnestly.\n" +
//                    "“Why did they live at the bottom of a well?”  The Dormouse again took a minute or two to think about it, and then said, “It was a treacle-well.”  “There’s no such thing!” Alice was beginning very angrily, but the Hatter and the March Hare went “Sh!\n" +
//                    "\n" +
//                    "CHAPTER VIII. The Queen’s Croquet-Ground\n" +
//                    "\n" +
//                    "“Would you tell me,” said Alice, a little timidly, “why you are painting those roses?”  Five and Seven said nothing, but looked at Two. Two began in a low voice, “Why the fact is, you see, Miss, this here ought to have been a _red_ rose-tree, and we put a white one in by mistake; and if the Queen was to find it out, we should all have our heads cut off, you know.\n" +
//                    "Next came the guests, mostly Kings and Queens, and among them Alice recognised the White Rabbit: it was talking in a hurried nervous manner, smiling at everything that was said, and went by without noticing her.\n" +
//                    "They’re dreadfully fond of beheading people here; the great wonder is, that there’s any one left alive!”  She was looking about for some way of escape, and wondering whether she could get away without being seen, when she noticed a curious appearance in the air: it puzzled her very much at first, but, after watching it a minute or two, she made it out to be a grin, and she said to herself “It’s the Cheshire Cat: now I shall have somebody to talk to.”  “How are you getting on?” said the Cat, as soon as there was mouth enough for it to speak with.\n" +
//                    "“I don’t think they play at all fairly,” Alice began, in rather a complaining tone, “and they all quarrel so dreadfully one can’t hear oneself speak—and they don’t seem to have any rules in particular; at least, if there are, nobody attends to them—and you’ve no idea how confusing it is all the things being alive; for instance, there’s the arch I’ve got to go through next walking about at the other end of the ground—and I should have croqueted the Queen’s hedgehog just now, only it ran away when it saw mine coming!”  “How do you like the Queen?” said the Cat in a low voice.\n" +
//                    "“Who _are_ you talking to?” said the King, going up to Alice, and looking at the Cat’s head with great curiosity.\n" +
//                    "When she got back to the Cheshire Cat, she was surprised to find quite a large crowd collected round it: there was a dispute going on between the executioner, the King, and the Queen, who were all talking at once, while all the rest were quite silent, and looked very uncomfortable.\n" +
//                    "The Cat’s head began fading away the moment he was gone, and, by the time he had come back with the Duchess, it had entirely disappeared; so the King and the executioner ran wildly up and down looking for it, while the rest of the party went back to the game.\n" +
//                    "\n" +
//                    "CHAPTER IX. The Mock Turtle’s Story\n" +
//                    "\n" +
//                    "“You can’t think how glad I am to see you again, you dear old thing!” said the Duchess, as she tucked her arm affectionately into Alice’s, and they walked off together.\n" +
//                    "It means much the same thing,” said the Duchess, digging her sharp little chin into Alice’s shoulder as she added, “and the moral of _that_ is—‘Take care of the sense, and the sounds will take care of themselves.’”  “How fond she is of finding morals in things!” Alice thought to herself.\n" +
//                    "“Come on, then,” said the Queen, “and he shall tell you his history,”  As they walked off together, Alice heard the King say in a low voice, to the company generally, “You are all pardoned.” “Come, _that’s_ a good thing!” she said to herself, for she had felt quite unhappy at the number of executions the Queen had ordered.\n" +
//                    "(If you don’t know what a Gryphon is, look at the picture.) “Up, lazy thing!” said the Queen, “and take this young lady to see the Mock Turtle, and to hear his history.\n" +
//                    "Come on!”  “Everybody says ‘come on!’ here,” thought Alice, as she went slowly after it: “I never was so ordered about in all my life, never!”  They had not gone far before they saw the Mock Turtle in the distance, sitting sad and lonely on a little ledge of rock, and, as they came nearer, Alice could hear him sighing as if his heart would break.\n" +
//                    "“This here young lady,” said the Gryphon, “she wants for to know your history, she do.”  “I’ll tell it her,” said the Mock Turtle in a deep, hollow tone: “sit down, both of you, and don’t speak a word till I’ve finished.”  So they sat down, and nobody spoke for some minutes.\n" +
//                    "“We called him Tortoise because he taught us,” said the Mock Turtle angrily: “really you are very dull!”  “You ought to be ashamed of yourself for asking such a simple question,” added the Gryphon; and then they both sat silent and looked at poor Alice, who felt ready to sink into the earth.\n" +
//                    "“We had the best of educations—in fact, we went to school every day—”  “_I’ve_ been to a day-school, too,” said Alice; “you needn’t be so proud as all that.”  “With extras?” asked the Mock Turtle a little anxiously.\n" +
//                    "\n" +
//                    "CHAPTER X. The Lobster Quadrille\n" +
//                    "\n" +
//                    "At last the Mock Turtle recovered his voice, and, with tears running down his cheeks, he went on again:—  “You may not have lived much under the sea—” (“I haven’t,” said Alice)—“and perhaps you were never even introduced to a lobster—” (Alice began to say “I once tasted—” but checked herself hastily, and said “No, never”) “—so you can have no idea what a delightful thing a Lobster Quadrille is!”  “No, indeed,” said Alice.\n" +
//                    "“What sort of a dance is it?”  “Why,” said the Gryphon, “you first form into a line along the sea-shore—”  “Two lines!” cried the Mock Turtle.\n" +
//                    "“Back to land again, and that’s all the first figure,” said the Mock Turtle, suddenly dropping his voice; and the two creatures, who had been jumping about like mad things all this time, sat down again very sadly and quietly, and looked at Alice.\n" +
//                    "“I’ve forgotten the words.”  So they began solemnly dancing round and round Alice, every now and then treading on her toes when they passed too close, and waving their forepaws to mark the time, while the Mock Turtle sang this, very slowly and sadly:—  “Will you walk a little faster?” said a whiting to a snail.\n" +
//                    "“You can really have no notion how delightful it will be When they take us up and throw us, with the lobsters, out to sea!” But the snail replied “Too far, too far!” and gave a look askance— Said he thanked the whiting kindly, but he would not join the dance.\n" +
//                    "“I don’t know where Dinn may be,” said the Mock Turtle, “but if you’ve seen them so often, of course you know what they’re like.”  “I believe so,” Alice replied thoughtfully.\n" +
//                    "“Soles and eels, of course,” the Gryphon replied rather impatiently: “any shrimp could have told you that.”  “If I’d been the whiting,” said Alice, whose thoughts were still running on the song, “I’d have said to the porpoise, ‘Keep back, please: we don’t want _you_ with us!’”  “They were obliged to have him with them,” the Mock Turtle said: “no wise fish would go anywhere without a porpoise.”  “Wouldn’t it really?” said Alice in a tone of great surprise.\n" +
//                    "And the Gryphon added “Come, let’s hear some of _your_ adventures.”  “I could tell you my adventures—beginning from this morning,” said Alice a little timidly: “but it’s no use going back to yesterday, because I was a different person then.”  “Explain all that,” said the Mock Turtle.\n" +
//                    "Soo—oop of the e—e—evening,     Beautiful, beauti—FUL SOUP!”   “Chorus again!” cried the Gryphon, and the Mock Turtle had just begun to repeat it, when a cry of “The trial’s beginning!” was heard in the distance.\n" +
//                    "\n" +
//                    "CHAPTER XI. Who Stole the Tarts?\n" +
//                    "\n" +
//                    "In the very middle of the court was a table, with a large dish of tarts upon it: they looked so good, that it made Alice quite hungry to look at them—“I wish they’d get the trial done,” she thought, “and hand round the refreshments!” But there seemed to be no chance of this, so she began looking at everything about her, to pass away the time.\n" +
//                    "“They can’t have anything to put down yet, before the trial’s begun.”  “They’re putting down their names,” the Gryphon whispered in reply, “for fear they should forget them before the end of the trial.”  “Stupid things!” Alice began in a loud, indignant voice, but she stopped hastily, for the White Rabbit cried out, “Silence in the court!” and the King put on his spectacles and looked anxiously round, to make out who was talking.\n" +
//                    "On this the White Rabbit blew three blasts on the trumpet, and then unrolled the parchment scroll, and read as follows:—  “The Queen of Hearts, she made some tarts,     All on a summer day: The Knave of Hearts, he stole those tarts,     And took them quite away!”   “Consider your verdict,” the King said to the jury.\n" +
//                    "“There’s a great deal to come before that!”  “Call the first witness,” said the King; and the White Rabbit blew three blasts on the trumpet, and called out, “First witness!”  The first witness was the Hatter.\n" +
//                    "“When did you begin?”  The Hatter looked at the March Hare, who had followed him into the court, arm-in-arm with the Dormouse.\n" +
//                    "“Give your evidence,” said the King; “and don’t be nervous, or I’ll have you executed on the spot.”  This did not seem to encourage the witness at all: he kept shifting from one foot to the other, looking uneasily at the Queen, and in his confusion he bit a large piece out of his teacup instead of the bread-and-butter.\n" +
//                    "“Give your evidence,” the King repeated angrily, “or I’ll have you executed, whether you’re nervous or not.”  “I’m a poor man, your Majesty,” the Hatter began, in a trembling voice, “—and I hadn’t begun my tea—not above a week or so—and what with the bread-and-butter getting so thin—and the twinkling of the tea—”  “The twinkling of the _what?_” said the King.\n" +
//                    "\n" +
//                    "CHAPTER XII. Alice’s Evidence\n" +
//                    "\n" +
//                    "She soon got it out again, and put it right; “not that it signifies much,” she said to herself; “I should think it would be _quite_ as much use in the trial one way up as the other.”  As soon as the jury had a little recovered from the shock of being upset, and their slates and pencils had been found and handed back to them, they set to work very diligently to write out a history of the accident, all except the Lizard, who seemed too much overcome to do anything but sit with its mouth open, gazing up into the roof of the court.\n" +
//                    "Special rules, set forth in the General Terms of Use part of this license, apply to copying and distributing Project Gutenberg-tm electronic works to protect the PROJECT GUTENBERG-tm concept and trademark.\n" +
//                    "If you do not agree to abide by all the terms of this agreement, you must cease using and return or destroy all copies of Project Gutenberg-tm electronic works in your possession.\n" +
//                    "If you paid a fee for obtaining a copy of or access to a Project Gutenberg-tm electronic work and you do not agree to be bound by the terms of this agreement, you may obtain a refund from the person or entity to whom you paid the fee as set forth in paragraph 1.E.8.\n" +
//                    "If an individual work is unprotected by copyright law in the United States and you are located in the United States, we do not claim a right to prevent you from copying, distributing, performing, displaying or creating derivative works based on the work as long as all references to Project Gutenberg are removed.\n" +
//                    "If you are outside the United States, check the laws of your country in addition to the terms of this agreement before downloading, copying, displaying, performing, distributing or creating derivative works based on this work or any other Project Gutenberg-tm work.\n" +
//                    "You may copy it, give it away or re-use it   under the terms of the Project Gutenberg License included with this   eBook or online at www.gutenberg.org.\n" +
//                    "If an individual Project Gutenberg-tm electronic work is derived from texts not protected by U.S. copyright law (does not contain a notice indicating that it is posted with permission of the copyright holder), the work can be copied and distributed to anyone in the United States without paying any fees or charges.\n" +
//                    "If an individual Project Gutenberg-tm electronic work is posted with the permission of the copyright holder, your use and distribution must comply with both paragraphs 1.E.1 through 1.E.7 and any additional terms imposed by the copyright holder.\n" +
//                    "Do not copy, display, perform, distribute or redistribute this electronic work, or any part of this electronic work, without prominently displaying the sentence set forth in paragraph 1.E.1 with active links or immediate access to the full terms of the Project Gutenberg-tm License.\n" +
//                    "Do not charge a fee for access to, viewing, displaying, performing, copying or distributing any Project Gutenberg-tm works unless you comply with paragraph 1.E.8 or 1.E.9.\n" +
//                    "Royalty   payments should be clearly marked as such and sent to the Project   Gutenberg Literary Archive Foundation at the address specified in   Section 4, \"Information about donations to the Project Gutenberg   Literary Archive Foundation.\"  * You provide a full refund of any money paid by a user who notifies   you in writing (or by e-mail) within 30 days of receipt that s/he   does not agree to the terms of the full Project Gutenberg-tm   License.\n" +
//                    "* You comply with all other terms of this agreement for free   distribution of Project Gutenberg-tm works.\n" +
//                    "If you wish to charge a fee or distribute a Project Gutenberg-tm electronic work or group of works on different terms than are set forth in this agreement, you must obtain permission in writing from the Project Gutenberg Literary Archive Foundation, the manager of the Project Gutenberg-tm trademark.\n" +
//                    "LIMITED WARRANTY, DISCLAIMER OF DAMAGES - Except for the \"Right of Replacement or Refund\" described in paragraph 1.F.3, the Project Gutenberg Literary Archive Foundation, the owner of the Project Gutenberg-tm trademark, and any other party distributing a Project Gutenberg-tm electronic work under this agreement, disclaim all liability to you for damages, costs and expenses, including legal fees.\n" +
//                    "\n"
//        )
//    }

    override suspend fun getBookSummaryContent(bookId: String): BookSummaryResponse {
        return withContext(Dispatchers.IO) {
            val bookSummary = BookSummaryResponse(
                bookIdAndBookResponseMap[bookId]?.bookId.toString(),
                bookIdAndBookResponseMap[bookId]?.bookName.toString(),
                bookIdAndBookResponseMap[bookId]?.authorName
            )
            val summary = try {
                if (bookIdAndSummaryText[bookId] != null) {
                    bookIdAndSummaryText[bookId]
                } else {
                    val response = api.fetchSummaryForBookId(bookIdAndBookResponseMap[bookId]?.summary ?: "")
//                    val response = api.fetchSummaryForBookId("https://firebasestorage.googleapis.com/v0/b/audino-327610.appspot.com/o/2554%2Fkshitij.json?alt=media&token=4ad0fc0f-cea1-4e64-ba07-3c4077f33099")
                    if (response.isSuccessful && response.body() != null) {
                        bookIdAndSummaryText[bookId] = response.body()?.summary ?: ""
                        response.body()?.summary ?: ""
                    } else {
                        ""
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
            Log.d("SummaryStuff", "$bookIdAndSummaryText")
            bookSummary.copy(
                summary = summary
            )
        }
    }
}