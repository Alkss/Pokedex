# Pokedex Application

This project uses the API from https://pokeapi.co/ to perform calls.

**The way the problem was approached**

The App consists of two different screens and each one of them is into a separate feature: *List* and *Detail*.

## List
Consists of a Screen of all the Pokemons retrieved by the API, the flow works in the following manner: It will perform a call to the API with the first 20 Pokemon, which right after will be saved on disk for easy access in the future.

Every time the Search Button is clicked two possible things can happen

#### Local Search:
If the Pokemon being searched was already retrieved by the API, it will be on disk and the search will give priority to the data coming from DB, this way is possible to search for partial strings.


#### Remote Search:
If the button is pressed with an empty text it will perform a search of the initial 20 Pokemons from the API

If the Pokemon being searched is not on the DB it will search for it at the API, however, due to the API behavior is necessary to write the complete name of the Pokemon in this scenario.

Upon clicking on the Pokemon the user is redirected to the Detail page.

### Detail
On the detail page, the user will be shown some information about the pokemon and be able to set it as a favorite or not.
**If the Pokemon is set as favorite but a search for the server is triggered the data will be overwritten in the DB, this is on purpose**
## Tech Stack

**Android:** Kotlin, Jetpack Compose, Room, Kotlin Courotines, Kotlin Flow, Retrofit, Gson


## Screenshots

![App Screenshot](https://i.gyazo.com/bbb65f319524c7ec7f4af9214cca1613.png)
![App Screenshot](https://i.gyazo.com/0a43bf157b92f0d99ff7f69d3309a4a1.png)
![App Screenshot](https://i.gyazo.com/675aa9e42244c0d36472bc0c3b0e1133.png)
![App Screenshot](https://i.gyazo.com/040e29ef01f58f8cc2c1734006d5538a.png)
![App Screenshot](https://i.gyazo.com/d5a068712a354c65b7cf56ec936881ac.png)


## Authors

- [@alkss](https://www.github.com/alkss)

