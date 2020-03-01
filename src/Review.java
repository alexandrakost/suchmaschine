public class Review {
    private Author author;
    private Document reviewedDocument;
    private String language;
    private Date releaseDate;
    private int rating;

    public Review (Author theAuthor, Document theReviewedDocument, String theLanguage,
                   Date theReleaseDate, int theRating){
        this.author = theAuthor;
        this.reviewedDocument = theReviewedDocument;
        this.language = theLanguage;
        this.releaseDate = theReleaseDate;
        this.rating = theRating;
    }

    public void setReviewedDocument (Document reviewedDocument) {
        this.reviewedDocument = reviewedDocument;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setRating(int rating) {
        if (rating >= 0 & rating <= 10) {
            this.rating = rating;
        }
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Document getReviewedDocument() {
        return reviewedDocument;
    }

    public String getLanguage() {
        return language;
    }

    public int getRating() {
        return rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Author getAuthor() {
        return author;
    }

    public int getAge (Date today){
        return this.releaseDate.getAgeInYears(today);
    }

    public String toString (){
        return "Review charachteristics: Review author: " + this.author + "; Reviewed document:"
                + this.reviewedDocument + "; Language: " + this.language + "; Release date: "
                + this.releaseDate + "; Rating:" + this.rating + ".";
    }

    public boolean equals (Review review) {
        if (review == null) {
            return false;
        }
        if (this.author.equals(review.author) && this.reviewedDocument.equals(review.reviewedDocument) &&
            this.language.equals(review.language) && this.releaseDate.equals(review.releaseDate) &&
            this.rating == review.rating){
            return true;
        }

        return false;
    }
}

