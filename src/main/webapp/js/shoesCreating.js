const FORM_ID = 'shoesCreatingForm';

document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById(FORM_ID);

    form.addEventListener("submit",
                          function (event) {
                              event.preventDefault()
                          });
});

const createShoes = () => {
    const form = document.getElementById(FORM_ID);
    const formData = new FormData(form);

    const scales = formData.getAll('scales')
                           ?.map(scale => {
                               const scaleObj = scale.split('-');
                               return {size: scaleObj[0], volume: scaleObj[1]};
                           }) || [];

    const vendorCode = formData.get('vendorCode');
    const shoes = {
        vendorCode,
        url: formData.get('url'),
        name: formData.get('name'),
        description: formData.get('description'),
        imageUrl: formData.get('imageUrl'),
        scales,
        seasonName: formData.get('seasonName'),
        modelName: formData.get('modelName'),
    };

    fetch(SHOES_URI,
          {
              method: 'POST',
              credentials: 'include',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify(shoes)
          })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server responded with code: ${response.status}`);
            }

            redirect('/shoes');
        })
        .catch(error => {
            console.error(`The shoes ${vendorCode} can not be created: `, error);
        });
};
