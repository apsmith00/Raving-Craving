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
        return
    else:
        global cuisine
        cuisine= s.find("a", class_="menu-tags")
        if(cuisine == None):
            cuisine="N/A"
        else:
            cuisine = cuisine.text
        dollar_rating = s.find("span", class_="active-dollar", string= True)#.text
        if(dollar_rating==None):
            dollar_rating="N/A"
        else:
            dollar_rating=dollar_rating.text
        address= s.find("a", class_="menu-address").text
        r_name=s.find("p", class_="title-header s-link")
        if(r_name==None):
            r_name="N/A"
        else:
            r_name=r_name.text
        number=s.find("a",class_="menu-phone-number phone-number-stickynav")
        if(number==None):
            number="N/A"
        else:
            number=number.text
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
        return df
def scrape_all(r_links):
    f_scrape = pd.DataFrame(columns = column_names)
    for x in r_links:
        f = scrape(x)
        f_scrape=pd.concat([f],ignore_index=True)
    return f_scrape
def main():
    final=scrape_all(complete_links)
    final.to_csv(r"C:\Users\alexa\fulllist.csv",encoding='utf-8',index=True)
main()
print("Finished")
#scrape("https://www.allmenus.com/tn/memphis/30350-china-wok/menu/")
    
