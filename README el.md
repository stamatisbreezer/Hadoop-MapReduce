# Hadoop-MapReduce
Aνάπτυξη προγραμμάτων MapReduce για την επεξεργασία μεγάλων δεδομένων στην πλατφόρμα Hadoop


- Νumeronyms
Δημιουργία και καταμέτρηση “numeronyms”. Τα numeronyms αντιστοιχούν σε λέξεις όπως παρακάτω:
s5n – shorten
h7k – hyperlink
l10n – localisation
i18n – internationalization

Ως numeronym μιας λέξης ορίζεται το αλφαριθμητικό που σχηματίζεται λαμβάνοντας τον πρώτο
και τον τελευταίο χαρακτήρα της λέξης, βάζοντας ενδιάμεσα το πλήθος των χαρακτήρων μεταξύ
του πρώτου και του τελευταίου. Αναλυτικότερα, το πρόγραμμα θα λαμβάνει υπόψη
του τις λέξεις με μήκος από 3 χαρακτήρες και πάνω, θα δημιουργεί numeronyms και στη
συνέχεια θα εκτυπώνει το πλήθος εμφάνισης του καθενός.

Το πρόγραμμα θα πρέπει να αγνοεί:
* τις λέξεις μικρότερες από 3 χαρακτήρες
* τα σημεία στίξης
* τα κεφαλαία / μικρά γράμματα, να είναι δηλαδή case-insensitive

Επίσης παραμετρικά θα δίνεται και το ελάχιστο πλήθος εμφανίσεων k ενός numeronym για το
οποίο ενδιαφερόμαστε. Το αποτέλεσμα του προγράμματος είναι μια λίστα με numeronyms και το
πλήθος εμφανίσεων του κάθε numeronyms που είναι μεγαλύτερο ή ίσο της παραμέτρου k που
δίνεται από το χρήστη.

Ως είσοδο το αρχείο SherlockHolmes.txt

Στην αναφορά σας να συμπεριλάβετε και τα αποτελέσματα εκτέλεσης για k = 10.

-----------

- Movie Analysis
Θα αναλύσουμε ένα dataset του IMDB σχετικά με ταινίες και θα βγάλουμε συμπεράσματα που θα βοηθήσουν την ομάδα του IMDB στην παροχή συστάσεων για
ταινίες ανά είδος και χώρα. Το dataset (movies.zip) που θα χρησιμοποιήσουμε περιλαμβάνει τα ακόλουθα στοιχεία:
imdbID
Τίτλο ταινίας
Έτος
Διάρκεια
Είδος/η
Ημερομηνία 1ης προβολής
Σκορ
Χώρα/ες


Αλγορίθμους MapReduce να υπολογίζουν τα ακόλουθα:
1. Τη συνολική διάρκεια όλων των ταινιών ανά χώρα. Να σημειωθεί ότι για συμπαραγωγές
μεταξύ χωρών, ο χρόνος της ταινίας μετράει για όλες τις χώρες.
2. Το σύνολο των ταινιών ανά χρονιά και ανά είδος που έχουν σκορ μεγαλύτερο του 8. Για
ταινίες που έχουν περισσότερα από ένα είδος, το άθροισμα θα είναι ξεχωριστό για κάθε
είδος. 
ΠΧ
2005_Horror 296
2015_Drama 162
…

Αν τα δεδομένα κάποιας γραμμής στο αρχείο δεν είναι αποδεκτά (π.χ. λάθος αριθμός στηλών) να αγνοήσετε τη συγκεκριμένη γραμμή.
Η αναφορά θα συμπεριλάμβάνει και τα αποτελέσματα εκτέλεσης για τα παραπάνω ερωτήματα.


----------

- DNA Sequence
Μία ακολουθία DNA αποτελείται από τα σύμβολα A, G, C, T. Για παράδειγμα, παρακάτω
δίνονται οι 4 πρώτες γραμμές του αρχείου εισόδου που αφορούν στην ακολουθία DNA του
βακτηρίου E. coli (https://en.wikipedia.org/wiki/Pathogenic_Escherichia_coli).

AGCTTTTCATTCTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATAGCAGC
TTCTGAACTGGTTACCTGCCGTGAGTAAATTAAAATTTTATTGACTTAGGTCACTAAATACTTTAACCAA
TATAGGCATAGCGCACAGACAGATAAAAATTACAGAGTACACAACATCCATGAAACGCATTAGCACCACC
ATTACCACCACCATCACCATTACCACAGGTAACGGTGCGGGCTGACGCGTACAGGAAACACAGAAAAAAG

Πρόγραμμα σε Java για Hadoop MapReduce το οποίο για κάθε γραμμή του
αρχείου εισόδου θα υπολογίζει το πλήθος εμφανίσεων συνεχόμενων 2-άδων, 3-άδων και
4-άδων της παραπάνω ακολουθίας DNA. 
Η επεξεργασία μίας γραμμής να είναι ανεξάρτητη από τις υπόλοιπες. 
Δηλαδή να μην ασχοληθείτε με την περίπτωση που σε μία 2-άδα, 3-άδα ή 4-άδα
συμβόλων κάποια σύμβολα βρίσκονται σε μία γραμμή και τα υπόλοιπα στην επόμενη.
Nα χρησιμοποιήσετε ως είσοδο το αρχείο ecoli.txt.

Τα αποτελέσματα που εμφανίζουν για κάθε 2-άδα, 3-άδα, 4-άδα το πλήθος εμφανίσεων θα
πρέπει να υπάρχουν σε ένα αρχείο txt (π.χ. output.txt)

-------

- Graphs
Δίνεται ένα αρχείο text όπου σε κάθε γραμμή υπάρχει μία σύνδεση μεταξύ δύο
κορυφών ενός δικτύου και μία τιμή πιθανότητας. Για κάθε ακμή e υπάρχει μία τιμή πιθανότητας
p(e) η οποία δηλώνει την πιθανότητα οι δύο κορυφές να συνδέονται με μία ακμη. Προφανώς οι
τιμές p(e) είναι μεταξύ 0 και 1. Οι τιμές σε κάθε γραμμή χωρίζονται με κενό χαρακτήρα.

graph.png

Έστω το δίκτυο που εμφανιζεται στο προηγούμενο σχήμα. Η ακμή που ενώνει τις κορυφές 4 και
5 έχει πιθανότητα 0.8, η ακμή που ενώνει τις κορυφές 2 και 3 έχει πιθανότητα 0.2, κλπ. Το
αρχείο που αντιστοιχεί στο γράφημα αυτό θα είναι:

1 2 0.6
1 3 0.9
2 3 0.2
3 4 0.75
2 5 0.2
4 5 0.8

Οι ακμές γενικά είναι αποθηκευμένες με τυχαία σειρά στο αρχείο, οπότε δεν μπορούμε να
υποθέσουμε ότι έχουν κάποια συγκεκριμένη διάταξη.

1. Nα υπολογίστει για όλες τις κορυφές ο μέσος βαθμός. 
Ο μέσος βαθμός ορίζεται ως το άθροισμα των πιθανοτήτων των ακμών που πέφτουν σε
μία κορυφή. Για παράδειγμα, στο προηγούμενο σχήμα ο μέσος βαθμός της κορυφής 3
είναι 0.9 + 0.2 + 0.75 = 1.85. 
Επίσης, πριν από τον υπολογισμό αυτό να αγνοήσετε όλες τις ακμές με τιμή πιθανότητας 
μικρότερη από ένα κατώφλι Τ το οποίο θα περνάει ως παράμετρος στη συνάρτηση main().

2. Να αλλάξετε τον κώδικα στο 1 έτσι ώστε στο τέλος να δίνονται στην έξοδο μόνο οι
κορυφές που έχουν μέσο βαθμό μεγαλύτερο από το μέσο όρων των βαθμών όλων των
κορυφών.

Nα χρησιμοποιήσετε ως είσοδο το αρχείο collins.txt.
Τα αποτελέσματα σε αρχείο txt.