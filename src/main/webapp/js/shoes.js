const SHOES_URI = '/shoes';

const reloadPage = () => {
    window.location.reload()
}

const deleteShoes = (vendorCode) => {
    fetch(SHOES_URI + '/' + vendorCode,
        {
            method: 'DELETE',
            credentials: 'include'
        })
        .then(reloadPage)
        .catch(error => {
            console.error(`The shoes ${vendorCode} can not be deleted: `, error);
        });
};
