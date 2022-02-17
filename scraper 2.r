################################
# Clear workspace  
rm(list = ls())

# Ctrl + L clears the console
################################


# First attempt at web scraping a specific restaurant with R
# Following template from list2 scraper

# Goal - to create data frame with the restaurant menu as seen in the webpage
#
#        Stage 1 - get one subsection of the menu in a data frame
#                  (Category, Dish Name, Price, Description)


#install.packages("rvest")
#install.packages("dplyr")

library(rvest)
library(dplyr)
library(stringr)

################################
#Create Variables
################################

link = "https://www.allmenus.com/tn/memphis/29283-exline-pizza-austin-peay/menu/"
page = read_html(link)

category = page %>% html_nodes(".menu-section-title") %>% html_text()
head(category)


dishname = page %>% html_nodes(".item-title") %>% html_text()
head(dishname)


price = page %>% html_nodes(".item-price") %>% html_text()
price <- trimws(price)
head(price)

description = page %>% html_nodes(".description") %>% html_text()
head(description)

# Creates data frame table of all entrees 
bigmenu <- data.frame(dishname, price, description)
names(bigmenu) <- c('Dish Name', 'Price', 'Dish Description')
head(bigmenu)



################################
# Stopped here. Not sure how to get this script to iterate over all of 
# the links in "https://www.allmenus.com/tn/memphis/-/"
################################


main_link = "https://www.allmenus.com/tn/memphis/-/american-new/"
page = read_html(main_link)
page_links = page %>% html_nodes(".name a") %>% html_attr("href") %>% paste(
  "https://www.allmenus.com/tn/memphis", ., sep = "")
head(page_links)


get_menu_entrees = function(page_links){
  
  page = read_html(page_links)
  
  dishname = page %>% html_nodes(".item-title") %>% html_text()
  price = page %>% html_nodes(".item-price") %>% html_text()
  price <- trimws(price)
  description = page %>% html_nodes(".description") %>% html_text()
  
  bigmenu <- data.frame(dishname, price, description)
  names(bigmenu) <- c('Dish Name', 'Price', 'Dish Description')
  head(bigmenu)

  
  #restaurant_entrees = page %>% html_nodes(".menu-section-title") %>% html_text() %>% paste(collapse = ",")
  return (bigmenu)
  
}

sections = sapply(page_Links, FUN = get_menu_sections)

restaurant_subsections = data.frame(name, street, city, sections)

# see a small section of the new data frame
head(restaurant_subsections)

