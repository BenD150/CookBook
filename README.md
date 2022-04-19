# CookBook
CookBook Application for CSE 5236 - Mobile Apps

Created by: Ben Dollenmayer, Daqian Zuo, and Max Hathaway 

## What is CookBook?
The CookBook app allows for saving and sharing of recipes! Here are all of the Use Cases: 
 

User Account Management 
- A user can create and delete accounts 
- A user can receive a forgot password email to change their password 
  - Or change their password in the user settings 
- A user can login and logout of their account 

Recipes 
- A user will be able to create a recipe 
  - A recipe includes name, prep time, cook time, instructions/ingredients needed, and an optional photo of the recipe 
  - A user can use the camera on their phone to upload an optional picture of the recipe 
- A user will be able to browse for new recipes 
  - The recipe has a save option so that the user can save it to their own profile’s saved recipes 
  - When searching for recipes, the user can search up the name of any recipe that they may be interested in, such as “Cheesecake” or “Pasta” 
- A user will be able to view their saved recipes 
  - A saved recipe is one that they create, or one that they find and save from other users for future reference 
  - They are also able to filter their saved recipes by Recipe Name 
  - The user can delete the saved recipe from their account 


## Firebase 
The CookBook app utilizes [Google's Firebase](https://firebase.google.com/docs) Realtime Database and Storage for storing photos of recipes.  

## Non-Functional Requirements 
- Firebase Offline Capabilities  
  - Firebase Recipe data is now stored on-device, so even if a user loses their Internet Connection, they are still able to see their saved recipes, and look through recipes that Firebase was able to load before they lost connection.  

- Loss of Internet Connection 
  - Whenever Internet connection is lost, the app continues to function as normal. However, a message is displayed to the user that they have no internet connection if they try to do the following things
  
        - Log In (if they weren’t already logged in before losing Internet connection) 
        - Register for a new account  
        - Change their password or Delete Account  
        - Upload a new recipe (as well as uploading a photo from their Camera) 
        - Save new recipes to their account  
        - Remove currently saved recipes  

- Screen Rotation 
  - There are layout files for both portrait and landscape screen orientation, and the application automatically keeps all data on the screen after orientation.  
  - The user can freely rotate their screen in all parts of the app with no crashes 

- Localization in Chinese 
  - If the user has Chinese as their phone’s language, the CookBook app UI automatically displays in Chinese as well. All the user uploaded data will remain unchanged to avoid possible inappropriate translation. 

- Performance NFR: Memory Leaks fixed with LeakCanary  
  - LeakCanary was used to fix memory leaks that were occurring with Firebase Event Listeners. This helped reduce memory usage of the Cookbook app. 

 
