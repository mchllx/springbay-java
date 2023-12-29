// Javascript code

//hamburger and nav
const hamburger = document.querySelector('.hamburger-menu');
const navMenu = document.querySelector('.nav-menu');

hamburger.addEventListener('click', () => {
    navMenu.classList.toggle('hide');
});

//image carousel
var slidePosition = 1;
slideShow(slidePosition);

// forward/Back controls
function plusSlides(n) {
  slideShow(slidePosition += n);
}

//  images controls
function currentSlide(n) {
  slideShow(slidePosition = n);
}

function slideShow(n) {
    var i;
    var slides = document.getElementsByClassName("Containers");
    var circles = document.getElementsByClassName("dots");

    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }

    for (i = 0; i < circles.length; i++) {
        circles[i].className = circles[i].className.replace(" enable", "");
    }

    //increments position
    slidePosition++;

    //reset to first slide
    if (slidePosition > slides.length) {
    slidePosition = 1;
    }

    slides[slidePosition-1].style.display = "block";
    circles[slidePosition-1].className += " enable";

    setTimeout(slideShow, 8000);
} 