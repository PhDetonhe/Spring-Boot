const PRODUCT_IMAGE_FALLBACK = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='300' height='300' viewBox='0 0 300 300'%3E%3Crect width='300' height='300' fill='%23FDF6EC'/%3E%3Ctext x='150' y='156' text-anchor='middle' font-family='Arial,sans-serif' font-size='20' fill='%238A8F8D'%3EProduto%3C/text%3E%3C/svg%3E";

function guessImageMime(base64) {
    if (base64.startsWith("/9j/")) return "image/jpeg";
    if (base64.startsWith("iVBORw0KGgo")) return "image/png";
    if (base64.startsWith("R0lGOD")) return "image/gif";
    if (base64.startsWith("UklGR")) return "image/webp";
    return "image/jpeg";
}

function productImageSrc(image, fallback = PRODUCT_IMAGE_FALLBACK) {
    if (!image || typeof image !== "string") return fallback;

    const value = image.trim();
    if (!value) return fallback;

    if (value.startsWith("data:image/") || value.startsWith("http://") || value.startsWith("https://")) {
        return value;
    }

    return `data:${guessImageMime(value)};base64,${value}`;
}

function readImageFileAsDataUrl(file) {
    return new Promise((resolve, reject) => {
        if (!file) {
            resolve("");
            return;
        }

        const reader = new FileReader();
        reader.onload = () => resolve(reader.result);
        reader.onerror = () => reject(new Error("Nao foi possivel ler a imagem."));
        reader.readAsDataURL(file);
    });
}

window.PetImages = {
    PRODUCT_IMAGE_FALLBACK,
    productImageSrc,
    readImageFileAsDataUrl
};
