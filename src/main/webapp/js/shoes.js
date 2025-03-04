const deleteShoes = (vendorCode) => {
    fetch(SHOES_URI + '/' + vendorCode,
          {
              method: 'DELETE',
              credentials: 'include'
          })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server responded with code: ${response.status}`);
            }

            reloadPage();
        })
        .catch(error => {
            console.error(`The shoes ${vendorCode} can not be deleted: `, error);
        });
};
