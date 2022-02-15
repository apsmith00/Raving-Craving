#Database should be two lists
#1. List of entrees where each entree has ingredients,restaurant, ect.
#2. List of restaurants which has location and food type(ie Indian or Vegan)


################################
# List 2 implementation

# Produces data frame with the following headings:
# ID, Restaurant Name, Cuisine, Street Address, City/ZIP Code
################################


# First attempt at web scraping with R
# Reference - https://www.youtube.com/watch?v=v8Yh_4oE-Fs

# Goal - to create data frame with basic restaurant information found
#        on the initial web page (Restaurant Name, Cuisine Type, Street Address, City)


#install.packages("rvest")
#install.packages("dplyr")

library(rvest)
library(dplyr)
library(stringr)

################################
#Create Variables
################################

link = "https://www.allmenus.com/tn/memphis/-/"
page = read_html(link)


#name = page %>% html_nodes(".name a") %>% html_text()
#head(name)

####################################################################################
selection_div = page %>% html_nodes(".description-container") %>% html_text()
head(selection_div)

# Trying to get rid of white space in selection_div 
# https://bookdown.org/lyzhang10/lzhang_r_tips_book/how-to-deal-with-empty-spaces.html

selection_div_1 <- trimws(selection_div)
head(selection_div_1)

selection_div_2 <- trimws(selection_div_1)
head(selection_div_2)

temp <- data.frame(selection_div_2)

id <-c(1:500)
temp1 <- data.frame(id, do.call("rbind", strsplit(as.character(temp$selection_div_2), "\n                    ", fixed = TRUE)))
names(temp1) <- c('ID', 'Restaurant Name', 'Cuisine')
head(temp1)

# set cuisine values that are the restaurant name as missing
newdata <- mutate(temp1, Cuisine = ifelse(temp1$Cuisine == temp1$`Restaurant Name`,NA, Cuisine))

head(newdata)
####################################################################################
#selection_div_2 <- gsub("\\s+"," ", selection_div_1)
#head(selection_div_2)

#div <- data.frame(selection_div)
####################################################################################

id <- c(1:500)
street = page %>% html_nodes(".delivery+ .address") %>% html_text()
city = page %>% html_nodes(".address+ .address") %>% html_text()

address = data.frame(id, street, city)
colnames(address) <- c('ID', 'Street Address', 'City / ZIP code')
head(address)
#write.csv(address, "test_1.csv")

####################################################################################

# merge two data frames by ID
list2 <- merge(newdata, address, by='ID')
head(list2)

#write.csv(initframe, "initframe.csv")

################################
