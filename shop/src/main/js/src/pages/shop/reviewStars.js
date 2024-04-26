export function reviewStars() {
    document.addEventListener('DOMContentLoaded',()=>{
        const ratings = document.querySelectorAll('.rating-class');
        ratings.forEach((rating)=>{
          const ratingNumber = parseInt(rating.getAttribute('id'));
          if(ratingNumber != 0){
            for(let i=0; i < 5; i++){
              const span = document.createElement('span')
              span.classList = "rating"
              if(i <= ratingNumber){
                span.innerHTML = "&#9733;"
              }else{
                span.innerHTML = "&#9734;"
              }
              rating.appendChild(span)
            }
          }else{
            const span = document.createElement('span')
            span.innerHTML="No reviews"
            rating.appendChild(span)
          }
        })
      })
}