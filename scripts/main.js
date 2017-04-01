var header;
var main;
var footer;
var domainModel;
var image;
var button;
var count = 0;

document.addEventListener("DOMContentLoaded", function() {
  header = document.getElementsByTagName('header')[0];
  main = document.getElementsByTagName('main')[0];
  footer = document.getElementsByTagName('footer')[0];
  domainModel = document.getElementById('domain');
  image = document.getElementsByTagName('img')[0];
  button = document.getElementsByTagName('button')[0];

  header.style.display = 'none';
  main.style.display = 'none';
  footer.style.display = 'none';
  domainModel.style.display = 'flex';
})


function showMockup() {
  if (count) {
    domainModel.style.display = 'initial';
    header.style.display = 'flex';
    main.style.display = 'flex';
    footer.style.display = 'flex';
  } else {
    image.src = 'images/domain_model_w_name.png';
    button.innerHTML = 'Visa mockup';
    count++;
  }

}
