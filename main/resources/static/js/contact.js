console.log("hello from contact.js file");
const viewContactModal= document.getElementById('view_contact_modal');
const baseUrl="http://localhost:9091";
//const baseUrl="https://www.aadhyajewellers.com/home";
//scm200.ap-south-1.elasticbeanstalk.com

console.log("i have been extecuteed ")
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

const instanceOptions={
    id:"viewContactModal",
    override:true,

};

const modal =new Modal(viewContactModal,options,instanceOptions);

function openContactModel(){
    modal.show();
}
function closeContactModal(){  

    modal.hide(); 
}
async function loadContactModel(id){
//     fetch(`http://localhost:8080/api/contacts/${id}`)
//     .then(async(response)=>{
//         let data =  await response.json();
//         console.log(data);
//         return data;
//     }).catch((error)=>{
//         console.log(error);
//     });
//   console.log(id);
  try{
    const data = await( await fetch(`${baseUrl}/api/contacts/${id}`)).json();
    console.log(data);
    document.querySelector("#contact_name").textContent=data.name;
    document.querySelector("#contact_email").textContent=data.email;
    document.querySelector("#contact_phone").textContent=data.phoneNumber;
    document.querySelector("#contact_address").textContent=data.address;
    document.querySelector("#contact_about").textContent=data.description;

    document.querySelector("#contact_website").href = data.websiteLink;
    document.querySelector("#contact_website").textContent=data.websiteLink;
    document.querySelector("#contact_linkedIn").href = data.linkedInLink;
    document.querySelector("#contact_linkedIn").textContent=data.linkedInLink;
    document.querySelector("#contact_image").src = data.picture;
    const contactFavorite = document.querySelector("#contact_favorite");
    if (data.favourite) {
      contactFavorite.innerHTML =
        "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
    } else {
      contactFavorite.innerHTML = "Not Favorite Contact";
    }
    openContactModel();
  } catch(error){
    console.log("error ",error);
  }

}

// delete contact 
async function deleteContact(id){
 
  console.log("deleted"+id);
  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!"
  }).then((result) => {
    if (result.isConfirmed) {
      const url=`${baseUrl}/user/contacts/delete/${id}`;
      window.location.replace(url);
      Swal.fire({
        title: "Deleted!",
        text: "Your file has been deleted.",
        icon: "success"
      });
    }
  });
}