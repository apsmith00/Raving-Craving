import requests
from bs4 import BeautifulSoup

URL = "https://www.allmenus.com/tn/memphis/-/"
response = requests.get(URL)
html= response.content
soup = BeautifulSoup(html, "html.parser")
#find_all container for each restaurant
#get name,cuisine, address1 and 2, and link
#scrape each link and add 


all_names = soup.find_all("h4")
restaurants=[]
for name in all_names[:len(all_names)]:
    restaurants.append(name.text)
cuisines = soup.find_all("p", class_="cuisine-list")
cuisine_list= []
for ctype in cuisines[:len(cuisines)]:
    cuisine_list.append(ctype.text)
addresses = soup.find_all("p", class_="address")
address_list = []
for add in addresses[:len(addresses)]:
    if(add == "Memphis, TN, 38016"):
        continue
    else:
        address_list.append(add.text)
all_links = soup.find_all("a",attrs={'href': re.compile("/tn")})
links=[]
for link in all_links[:len(all_links)]:
    all_links.append(link)
data={"Name":restaurants,
     "Cuisine":cuisine_list,
     "Link":all_links} #addresses must be fixed later for size
print(data)
