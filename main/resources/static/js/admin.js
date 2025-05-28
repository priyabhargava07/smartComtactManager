console.log("admin.js");

document.querySelector("#image_file_input").addEventListener('change',function(event){
    let file =event.target.files[0];
    let render = new FileReader();
    render.onload=function(){
        document.getElementById("upload_image_preview").src=render.result;

    }
    render.readAsDataURL(file);
})