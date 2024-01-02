document.addEventListener('DOMContentLoaded', function () {
    var countDownDate = new Date("Jan 3, 2024 09:00:00").getTime();
    var x = setInterval(function() {
        var now = new Date().getTime();
        var distance = countDownDate - now;

        var days = Math.floor (distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000); 

        document.getElementById("days").innerHTML = days;
        document.getElementById("hours").innerHTML = hours;
        document.getElementById("minutes").innerHTML = minutes;
        document.getElementById("seconds").innerHTML = seconds;

        if (distance < 0) {
            console.log("Resetting countdown");
            clearInterval(x);

        document.getElementById("days").innerHTML = "00";
        document.getElementById("hours").innerHTML = "00";
        document.getElementById("minutes").innerHTML = "00";
        document.getElementById("seconds").innerHTML = "00";
        }

    }, 1000);

        //  images controls
        function currentSlide(n) {
        slideShow(slidePosition = n);
        }

        //image carousel
        var slidePosition = 1;
        slideShow(slidePosition);

        // forward/Back controls
        function plusSlides(n) {
        slideShow(slidePosition += n);
        }

        var dots = document.querySelectorAll('.dots');
        dots.forEach(function(dot, index) {
            dot.addEventListener('click', function() {
                currentSlide(index + 1);
            });
        });

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
    
})
