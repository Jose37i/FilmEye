package com.example.josemiranda.filmeye;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class JSONParser {

    /**
     * This method is parsing the movie details
     * @param movieName is passed
     * @return
     * It retruns a String array of all the data
     * @throws IOException
     */

    public String []  parse(String movieName) throws IOException {
        /**
         * parse results to obtain movie ID
         */
        String [] parsedData = new String[10];
        //String url = "https://api.themoviedb.org/3/search/movie?api_key=f91e53ad349204db01179d648b57a758&query=Iron+man";
        String url = firstURLBuilder(movieName);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        //add request header
        System.out.println("\nSending 'GET' request to URL : " + url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);

        }
        in.close();

        /*************************************************
         * Parsed the return for the top results movie id
         **************************************************/
        int movieID=0;
        try {
            int idLocation = response.indexOf("id");
            int colonPosition = response.indexOf(":", idLocation);
            int idLocationStop = response.indexOf(",", colonPosition);
            System.out.println(idLocation + " " + colonPosition + " " + idLocationStop);
            String movieIDString = response.substring(colonPosition + 1, idLocationStop);
            movieID = Integer.parseInt(movieIDString);
            parsedData[9] = "" + movieID;
        }
        catch (IndexOutOfBoundsException ex)
        {
            parsedData[0] = "No results found";
            return parsedData;
        }
        String newURL = urlBuilder(movieID);

        URL obj2 = new URL(newURL);
        HttpURLConnection connection = (HttpURLConnection) obj2.openConnection();
        connection.setRequestMethod("GET");
        //add request header
        System.out.println("\nSending 'GET' request to URL : " + newURL);
        BufferedReader input = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine2;
        StringBuffer response2 = new StringBuffer();
        while ((inputLine2 = input.readLine()) != null) {
            response2.append(inputLine2);

        }
        input.close();


        /*******************************************************
         * Parse to find the title of the movie
         ******************************************************/
        int titleLocation = response2.indexOf("title");
        int colons = response2.indexOf(":", titleLocation);
        int titleLocationStop = response2.indexOf(",", colons);
        String title = response2.substring(colons + 2, titleLocationStop - 1);
        parsedData[0] = "Title: "+ title;

        /*******************************************************
         * Parse to find the Synopsis of the movie
         ******************************************************/
        int synopsis = response2.indexOf("overview");
        int colons3 = response2.indexOf(":", synopsis);
        int synopsisStop = response2.indexOf("\"", colons3 + 2); //added +2 to the colons to compensate for the double quotes right before the synopsis begins
        String realSynopsis = response2.substring(colons3 + 2, synopsisStop - 1);
        parsedData[1] = "Synopsis: "+ realSynopsis;

        /*******************************************************
         * Parse to find the release date of the movie
         ******************************************************/
        int releaseDate = response2.indexOf("release_date");
        int colonsForRelease = response2.indexOf(":", releaseDate);
        int releaseDateStop = response2.indexOf("\"", colonsForRelease + 3); //added +3 to compensate for the characters before the release dates
        String realReleaseDate = response2.substring(colonsForRelease + 2, releaseDateStop);
        parsedData[2] = "Release Date: " + realReleaseDate;

        /*******************************************************
         * Parse to find the runtime date of the movie
         ******************************************************/
        int runtime = response2.indexOf("runtime");
        int colonsForRuntime = response2.indexOf(":", runtime);
        int runtimeStop = response2.indexOf(",", colonsForRuntime);
        String realRuntime = response2.substring(colonsForRuntime + 1, runtimeStop);
        parsedData[3] = "Runtime(mins): " + realRuntime;


        /*******************************************************
         * Parse to find the vote average of the movie
         ******************************************************/
        int voteAverage = response2.indexOf("vote_average");
        int colonsForVotes = response2.indexOf(":", voteAverage);
        int voteStop = response2.indexOf(",", colonsForVotes);
        String realVoteStop = response2.substring(colonsForVotes + 1, voteStop);

        int voteCount = response2.indexOf("vote_count");
        int colonsForVoteCount = response2.indexOf(":", voteCount);
        int voteCountStop = response2.indexOf("}", colonsForVoteCount); //added +3 to compensate for the characters before the release dates
        String realVoteCount = response2.substring(colonsForVoteCount + 1, voteCountStop);
        parsedData[4] = "Review Average is : " + realVoteStop + " from " + realVoteCount + " reviews";
        /*******************************************************
         * Parse to find the budget  of the movie
         ******************************************************/
        int budget = response2.indexOf("budget");
        int colonsForBudget = response2.indexOf(":", budget);
        int budgetStop = response2.indexOf(",", colonsForBudget); //added +3 to compensate for the characters before the release dates
        String realBudget = response2.substring(colonsForBudget + 1, budgetStop);
        int budgetBefore = Integer.parseInt(realBudget);

        //System.out.println("Budget: $" + String.format("%,d", budgetBefore));
        parsedData[5] = "Budget: $" + String.format("%,d", budgetBefore);


        /*******************************************************
         * Parse to find the website  of the movie
         ******************************************************/
        int webSite = response2.indexOf("homepage");
        int colonForWebsite = response2.indexOf(":", webSite);
        int websiteStop = response2.indexOf(",", colonForWebsite); //added +3 to compensate for the characters before the release dates
        String realWebsite = response2.substring(colonForWebsite + 2, websiteStop - 1);
        parsedData[6] = "Website: " + realWebsite;

        /*******************************************************
         * Parse to find the genre(s)  of the movie
         ******************************************************/
        int genreString = response2.indexOf("genres");
        int endOfGenreString = response2.indexOf("]", genreString);
        String realGenresString = response2.substring(genreString, endOfGenreString + 1);
        String allGenres = "";
        //System.out.println("Genres: " + realGenresString);
        while (true) {
            try {
                int genreGrab = realGenresString.indexOf("name");
                int colonForGenreGrab = realGenresString.indexOf(":", genreGrab);
                int genreGrabEnd = realGenresString.indexOf("}", colonForGenreGrab); //added +3 to compensate for the characters before the release dates
                String genre2 = realGenresString.substring(colonForGenreGrab + 2, genreGrabEnd - 1);
                allGenres = allGenres + genre2 + ", ";
                realGenresString = realGenresString.substring(genreGrabEnd);
                //System.out.println(allGenres);
                //System.out.println(realGenresString);
            } catch (IndexOutOfBoundsException ex) {
                break;

            }

        }
        parsedData[7] = "Genres: " + allGenres;

        /*******************************************************
         * Parse to find the production companies  of the movie
         ******************************************************/
        int productionCompanyString = response2.indexOf("production_companies");
        int endOfProdCompanyString = response2.indexOf("]", productionCompanyString);
        String realProductionString = response2.substring(productionCompanyString, endOfProdCompanyString + 1);
        String allProductionCompanies = "";
        //System.out.println("Production Companies : " + realProductionString);
        while (true) {
            try {
                int productionCompanyGrab = realProductionString.indexOf("name");
                int colonForCompanyGrab = realProductionString.indexOf(":", productionCompanyGrab);
                int productionGrabEnd = realProductionString.indexOf(",", colonForCompanyGrab); //added +3 to compensate for the characters before the release dates
                String production2 = realProductionString.substring(colonForCompanyGrab + 2, productionGrabEnd - 1);
                allProductionCompanies = allProductionCompanies + production2 + ", ";
                realProductionString = realProductionString.substring(productionGrabEnd);
                //System.out.println(allProductionCompanies);
                //System.out.println(realProductionString);
            } catch (IndexOutOfBoundsException ex) {
                break;

            }

        }
        parsedData[8] = "Produced By : " + allProductionCompanies;
        return parsedData;
    }

    /**
     * Method to parse genre return
     * @param genres
     * it takes genres as a parameter
     * @return
     * returns the movies realting to those genres
     * @throws IOException
     */
    public String genreParse(String genres) throws IOException {
        String genreURL = genreURLBuilder(genres);
        URL obj = new URL(genreURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        //add request header
        System.out.println("\nSending 'GET' request to URL : " + genreURL);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response3 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response3.append(inputLine);

        }
        in.close();

        String realTitleString = response3.toString();
        String allTitles = "";

        for(int i =0; true; i++) {
            try {
                int titleGrab = realTitleString.indexOf("title");
                int colonForTitleGrab = realTitleString.indexOf(":", titleGrab);
                int titleGrabEnd = realTitleString.indexOf(",", colonForTitleGrab);//added +3 to compensate for the characters before the release dates
                String title2 = realTitleString.substring(colonForTitleGrab + 2, titleGrabEnd - 1);
                int realEnd = realTitleString.indexOf("}",titleGrab);
                allTitles = allTitles + title2 + "\n";
                realTitleString = realTitleString.substring(realEnd);

            } catch (IndexOutOfBoundsException ex) {
                if(i==0)
                {
                    return "No results found, click back to search again";
                }
                break;

            }

        }



        return allTitles;
    }

    /**
     * This method parses the person results
     * @param aPerson
     * it takes aPerson as a paramter
     * @return
     * It returns some movies they been in
     * @throws IOException
     */

    public String personParse(String aPerson) throws IOException
    {
        String personURL = personURLBuilder(aPerson);
        URL obj = new URL(personURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        //add request header
        System.out.println("\nSending 'GET' request to URL : " + personURL);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response7 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response7.append(inputLine);

        }
        in.close();

        //int films = response7.indexOf("known_for");
        String realFilmString = response7.toString();
        String allFilms = "Some Filmography: \n";
        for(int j =0; true; j++) {
            try {
                if(j==0)
                {
                    int films = response7.indexOf("known_for");
                    realFilmString = response7.substring(films);
                }
                int filmGrab = realFilmString.indexOf("title");
                int colonForFilmGrab = realFilmString.indexOf(":", filmGrab);
                int filmGrabEnd = realFilmString.indexOf(",", colonForFilmGrab); //added +3 to compensate for the characters before the release dates
                int realFilmGrabEnd = realFilmString.indexOf("}",filmGrabEnd);
                String films2 = realFilmString.substring(colonForFilmGrab + 2, filmGrabEnd - 1);
                allFilms = allFilms + films2 + "\n";
                realFilmString = realFilmString.substring(realFilmGrabEnd);
                //System.out.println(allProductionCompanies);
                //System.out.println(realProductionString);
            } catch (IndexOutOfBoundsException ex) {
                if(j==0)
                {
                    return "No results found, click back to search again";
                }
                break;

            }

        }
        return allFilms ;
    }


    /**
     * method to build the URL for searching for a movie
     * @param movieID
     * adds movieId to the url
     * @return
     * the url
     */
    private static String urlBuilder(int movieID)
    {
        return  "https://api.themoviedb.org/3/movie/" + movieID + "?api_key=f91e53ad349204db01179d648b57a758&language=en-US";
    }

    private static String firstURLBuilder(String movieName)
    {
        while (true) {
            movieName = movieName.trim();
            if (movieName.contains(" ")) {
                int space = movieName.indexOf(" ");
                String firstHalf = movieName.substring(0, space);
                String secondHalf = movieName.substring(space+1);
                movieName = firstHalf + "+" + secondHalf;
            }
            else {
                break;
            }
        }
        return "https://api.themoviedb.org/3/search/movie?api_key=f91e53ad349204db01179d648b57a758&query=" +movieName;
    }

    private static String genreURLBuilder(String genres)
    {
        String genreValue = "";
        for(int i =0; true; i++) {
            String temp;
            int indexOfComma = genres.indexOf(",");
            if(i>0)
            {
                genreValue = genreValue + "%2C";
            }
            if(indexOfComma==-1)
            {
                temp = genres.trim();
            }
            else
            {
                temp = genres.substring(0, indexOfComma).trim();

            }

            if ("Action".equalsIgnoreCase(temp)) {
                genreValue = genreValue+ "28";
            } else if ("Adventure".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"12";
            } else if ("Animation".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"16";
            } else if ("Comedy".equalsIgnoreCase(temp)) {
                genreValue =genreValue+ "35";
            } else if ("Crime".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"80";
            } else if ("Documentary".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"99";
            } else if ("Drama".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"18";
            } else if ("Family".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"10751";
            } else if ("Fantasy".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"15";
            } else if ("History".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"36";
            } else if ("Horror".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"27";
            } else if ("Music".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"10402";
            } else if ("Mystery".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"9648";
            } else if ("Romance".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"10749";
            } else if ("Science Fiction".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"878";
            } else if ("TV Movie".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"10770";
            } else if ("Thriller".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"53";
            } else if ("War".equalsIgnoreCase(temp)) {
                genreValue =genreValue+ "10752";
            } else if ("Western".equalsIgnoreCase(temp)) {
                genreValue = genreValue+"37";
            }
            if(indexOfComma==-1)
            {
                break;
            }
            else
            {
                genres = genres.substring(indexOfComma+1).trim();
            }

        }
        return "https://api.themoviedb.org/3/discover/movie?api_key=f91e53ad349204db01179d648b57a758&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres=" + genreValue;
    }

    private static String personURLBuilder(String person)
    {
        String result=null;
        while (true) {
            person = person.trim();
            if (person.contains(" ")) {
                int space = person.indexOf(" ");
                String firstHalf = person.substring(0, space);
                String secondHalf = person.substring(space+1);
                person = firstHalf + "%20" + secondHalf;
            }
            else {
                break;
            }
        }
        return "https://api.themoviedb.org/3/search/person?api_key=f91e53ad349204db01179d648b57a758&language=en-US&query=" + person + "&page=1&include_adult=false";
    }






}
