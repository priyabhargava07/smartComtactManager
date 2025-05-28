console.log("script loaded");

let currentTheme = getTheme();
document.addEventListener('DOMContentLoaded',()=>{
    changeTheme();
});
console.log("theme: "+currentTheme);
function changeTheme(){
    changePageTheme(currentTheme,currentTheme);
    const btn = document.querySelector('#theme_change_button');
   
    btn.addEventListener('click',(event)=>{
    let oldTheme = currentTheme;
     console.log("clicked");
    if(currentTheme === "light"){ currentTheme="dark";}
    else{currentTheme="light";}
    setTheme(currentTheme);
    console.log("theme: "+currentTheme);
    changePageTheme(currentTheme,oldTheme);
   });
}

function setTheme(theme){
    localStorage.setItem("theme",theme);
}

function getTheme(){
 let theme = localStorage.getItem("theme");
 return theme ? theme : "light";    
}
function changePageTheme(theme,oldTheme){
   setTheme(currentTheme);
   document.querySelector('html').classList.remove(oldTheme);
   document.querySelector('html').classList.add(theme);
   document.querySelector('#theme_change_button').querySelector('span').textContent = theme === "light" ? "Dark" : "Light";
}