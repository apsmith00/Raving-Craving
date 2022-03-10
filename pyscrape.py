import re
import pandas as pd
import requests
from bs4 import BeautifulSoup

URL = "https://www.allmenus.com/tn/memphis/-/"
url1="https://www.allmenus.com/tn/memphis/"
response = requests.get(URL)
html=response.content
soup=BeautifulSoup(html,"html.parser")
links=[]
complete_links=[]
column_names=["restaurant","entree","price","description","cuisine"]
global elist
elist =pd.DataFrame(columns=column_names)
lst=[]
r=0
for a in soup.find_all('a', href=True): 
    if a.text: 
        links.append(a['href'])
for i in range(50):
    del links[0]
links=links[1::2]
links=links[:-4]
for x in links:
    complete_links.append(url1+x)
def success(link):
    if(response.status_code !=200):
        return False
    else:
        return True
def scrape(r_link):
    response = requests.get(r_link)
    html=response.content
    s=BeautifulSoup(html,"html.parser")
    if(success(r_link)!=True):
        print("Link Error")
        return -1;
    else:
        print("Success")
        global cuisine
        cuisine= s.find("a", class_="menu-tags").text
        dollar_rating = s.find("span", class_="active-dollar").text
        address= s.find("a", class_="menu-address").text
        r_name=s.find("p", class_="title-header s-link").text
        number=s.find("a",class_="menu-phone-number phone-number-stickynav").text
        items=[]
        for i in s.find_all("li",class_="menu-items"):
            items.append(i)
        for li in items:
                e_name=li.find("span",class_="item-title").text
                e_price=li.find("span",class_="item-price").text.strip()
                e_descript=li.find("p",class_="description").text
                row={"restaurant":r_name,"entree":e_name,"price":e_price,"description":e_descript,"cuisine":cuisine}
                global lst
                lst.append(row)
                global elist
        df=pd.DataFrame(lst, columns=column_names)
        df.to_csv(r"C:\Users\alexa\temp1.csv",encoding='utf-8',index=True)
    
scrape("https://www.allmenus.com/tn/memphis/30350-china-wok/menu/")
    
