// Sample book data
const books = [
    {
        id: 1,
        title: "The Great Gatsby",
        author: "F. Scott Fitzgerald",
        price: 12.99,
        image: "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=400&fit=crop&crop=face",
        category: "Fiction"
    },
    {
        id: 2,
        title: "To Kill a Mockingbird",
        author: "Harper Lee",
        price: 14.99,
        image: "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=300&h=400&fit=crop&crop=face",
        category: "Fiction"
    },
    {
        id: 3,
        title: "1984",
        author: "George Orwell",
        price: 13.99,
        image: "https://images.unsplash.com/photo-1495640388908-05fa85288e61?w=300&h=400&fit=crop&crop=face",
        category: "Fiction"
    },
    {
        id: 4,
        title: "Pride and Prejudice",
        author: "Jane Austen",
        price: 11.99,
        image: "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=300&h=400&fit=crop&crop=face",
        category: "Romance"
    },
    {
        id: 5,
        title: "The Catcher in the Rye",
        author: "J.D. Salinger",
        price: 15.99,
        image: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300&h=400&fit=crop&crop=face",
        category: "Fiction"
    },
    {
        id: 6,
        title: "A Brief History of Time",
        author: "Stephen Hawking",
        price: 18.99,
        image: "https://images.unsplash.com/photo-1446776653964-20c1d3a81b06?w=300&h=400&fit=crop&crop=face",
        category: "Science"
    },
    {
        id: 7,
        title: "The Origin of Species",
        author: "Charles Darwin",
        price: 16.99,
        image: "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=300&h=400&fit=crop&crop=face",
        category: "Science"
    },
    {
        id: 8,
        title: "Harry Potter and the Sorcerer's Stone",
        author: "J.K. Rowling",
        price: 19.99,
        image: "https://images.unsplash.com/photo-1621351183012-e2f9972dd9bf?w=300&h=400&fit=crop&crop=face",
        category: "Kids"
    },
    {
        id: 9,
        title: "Charlotte's Web",
        author: "E.B. White",
        price: 9.99,
        image: "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=300&h=400&fit=crop&crop=face",
        category: "Kids"
    },
    {
        id: 10,
        title: "The Notebook",
        author: "Nicholas Sparks",
        price: 13.99,
        image: "https://images.unsplash.com/photo-1519904981063-b0cf448d479e?w=300&h=400&fit=crop&crop=face",
        category: "Romance"
    },
    {
        id: 11,
        title: "Jane Eyre",
        author: "Charlotte Brontë",
        price: 12.99,
        image: "https://images.unsplash.com/photo-1509021436665-8f07dbf5bf1d?w=300&h=400&fit=crop&crop=face",
        category: "Romance"
    },
    {
        id: 12,
        title: "Cosmos",
        author: "Carl Sagan",
        price: 17.99,
        image: "https://images.unsplash.com/photo-1446776877081-d282a0f896e2?w=300&h=400&fit=crop&crop=face",
        category: "Science"
    }
];

// Cart functionality
class CartManager {
    constructor() {
        this.cart = this.loadCart();
        this.updateCartBadge();
    }

    loadCart() {
        const savedCart = localStorage.getItem('bookstoreCart');
        return savedCart ? JSON.parse(savedCart) : [];
    }

    saveCart() {
        localStorage.setItem('bookstoreCart', JSON.stringify(this.cart));
        this.updateCartBadge();
    }

    addToCart(bookId) {
        const book = books.find(b => b.id === bookId);
        if (!book) return false;

        const existingItem = this.cart.find(item => item.id === bookId);
        if (existingItem) {
            existingItem.quantity += 1;
        } else {
            this.cart.push({
                ...book,
                quantity: 1
            });
        }

        this.saveCart();
        return true;
    }

    removeFromCart(bookId) {
        this.cart = this.cart.filter(item => item.id !== bookId);
        this.saveCart();
    }

    updateQuantity(bookId, quantity) {
        const item = this.cart.find(item => item.id === bookId);
        if (item) {
            if (quantity <= 0) {
                this.removeFromCart(bookId);
            } else {
                item.quantity = quantity;
                this.saveCart();
            }
        }
    }

    clearCart() {
        this.cart = [];
        this.saveCart();
    }

    getCartTotal() {
        return this.cart.reduce((total, item) => total + (item.price * item.quantity), 0);
    }

    getCartItemCount() {
        return this.cart.reduce((total, item) => total + item.quantity, 0);
    }

    updateCartBadge() {
        const badge = document.getElementById('cartBadge');
        if (badge) {
            const count = this.getCartItemCount();
            badge.textContent = count;
            badge.style.display = count > 0 ? 'flex' : 'none';
        }
    }
}

// Initialize cart manager
const cartManager = new CartManager();

// Toast notification
function showToast(message) {
    const toast = document.getElementById('toast');
    const toastMessage = document.getElementById('toastMessage');
    
    if (toast && toastMessage) {
        toastMessage.textContent = message;
        toast.classList.add('show');
        
        setTimeout(() => {
            toast.classList.remove('show');
        }, 3000);
    }
}

// Load books for homepage
function loadFeaturedBooks() {
    const featuredBooksGrid = document.getElementById('featuredBooksGrid');
    if (!featuredBooksGrid) return;

    const featuredBooks = books.slice(0, 6);
    featuredBooksGrid.innerHTML = featuredBooks.map(book => `
        <div class="book-card">
            <img src="${book.image}" alt="${book.title}" class="book-image">
            <div class="book-info">
                <h3 class="book-title">${book.title}</h3>
                <p class="book-author">by ${book.author}</p>
                <p class="book-price">$${book.price.toFixed(2)}</p>
                <button class="btn btn-primary" onclick="addToCartAndNotify(${book.id})">
                    <i class="fas fa-shopping-cart"></i>
                    Add to Cart
                </button>
            </div>
        </div>
    `).join('');
}

// Load books for books page
function loadAllBooks() {
    const booksGrid = document.getElementById('booksGrid');
    if (!booksGrid) return;

    booksGrid.innerHTML = books.map(book => `
        <div class="book-card">
            <img src="${book.image}" alt="${book.title}" class="book-image">
            <div class="book-info">
                <h3 class="book-title">${book.title}</h3>
                <p class="book-author">by ${book.author}</p>
                <p class="book-price">$${book.price.toFixed(2)}</p>
                <button class="btn btn-primary" onclick="addToCartAndNotify(${book.id})">
                    <i class="fas fa-shopping-cart"></i>
                    Add to Cart
                </button>
            </div>
        </div>
    `).join('');
}

// Add to cart with notification
function addToCartAndNotify(bookId) {
    const success = cartManager.addToCart(bookId);
    if (success) {
        const book = books.find(b => b.id === bookId);
        showToast(`"${book.title}" added to cart!`);
    }
}

// Load cart items
function loadCartItems() {
    const cartItems = document.getElementById('cartItems');
    const emptyCart = document.getElementById('emptyCart');
    const cartSummary = document.getElementById('cartSummary');
    
    if (!cartItems || !emptyCart || !cartSummary) return;

    if (cartManager.cart.length === 0) {
        emptyCart.style.display = 'block';
        cartSummary.style.display = 'none';
        cartItems.innerHTML = '';
        return;
    }

    emptyCart.style.display = 'none';
    cartSummary.style.display = 'block';

    cartItems.innerHTML = cartManager.cart.map(item => `
        <div class="cart-item">
            <img src="${item.image}" alt="${item.title}" class="cart-item-image">
            <div class="cart-item-info">
                <h3 class="cart-item-title">${item.title}</h3>
                <p class="cart-item-author">by ${item.author}</p>
                <p class="cart-item-price">$${item.price.toFixed(2)}</p>
            </div>
            <div class="quantity-controls">
                <button class="quantity-btn" onclick="updateCartQuantity(${item.id}, ${item.quantity - 1})">
                    <i class="fas fa-minus"></i>
                </button>
                <span class="quantity">${item.quantity}</span>
                <button class="quantity-btn" onclick="updateCartQuantity(${item.id}, ${item.quantity + 1})">
                    <i class="fas fa-plus"></i>
                </button>
            </div>
            <button class="remove-btn" onclick="removeFromCart(${item.id})">
                <i class="fas fa-times"></i>
            </button>
        </div>
    `).join('');

    updateCartSummary();
}

// Update cart quantity
function updateCartQuantity(bookId, quantity) {
    cartManager.updateQuantity(bookId, quantity);
    loadCartItems();
}

// Remove from cart
function removeFromCart(bookId) {
    cartManager.removeFromCart(bookId);
    loadCartItems();
}

// Update cart summary
function updateCartSummary() {
    const subtotal = document.getElementById('subtotal');
    const total = document.getElementById('total');
    
    if (subtotal && total) {
        const cartTotal = cartManager.getCartTotal();
        subtotal.textContent = `$${cartTotal.toFixed(2)}`;
        total.textContent = `$${cartTotal.toFixed(2)}`;
    }
}

// Clear cart
function clearCart() {
    if (confirm('Are you sure you want to clear your cart?')) {
        cartManager.clearCart();
        loadCartItems();
    }
}

// Newsletter form validation
function handleNewsletterSubmit(event) {
    event.preventDefault();
    const emailInput = document.getElementById('newsletterEmail');
    
    if (emailInput && emailInput.value.trim()) {
        showToast('Thank you for subscribing to our newsletter!');
        emailInput.value = '';
    }
}

// Contact form validation
function handleContactSubmit(event) {
    event.preventDefault();
    
    const name = document.getElementById('name');
    const email = document.getElementById('email');
    const message = document.getElementById('message');
    const contactForm = document.getElementById('contactForm');
    const successMessage = document.getElementById('successMessage');
    
    // Clear previous errors
    document.querySelectorAll('.error-message').forEach(error => {
        error.textContent = '';
    });
    
    let isValid = true;
    
    // Validate name
    if (!name.value.trim()) {
        document.getElementById('nameError').textContent = 'Name is required';
        isValid = false;
    }
    
    // Validate email
    if (!email.value.trim()) {
        document.getElementById('emailError').textContent = 'Email is required';
        isValid = false;
    } else if (!isValidEmail(email.value)) {
        document.getElementById('emailError').textContent = 'Please enter a valid email address';
        isValid = false;
    }
    
    // Validate message
    if (!message.value.trim()) {
        document.getElementById('messageError').textContent = 'Message is required';
        isValid = false;
    }
    
    if (isValid) {
        contactForm.style.display = 'none';
        successMessage.style.display = 'block';
    }
}

// Email validation helper
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Mobile menu toggle (basic implementation)
function toggleMobileMenu() {
    const nav = document.querySelector('.nav');
    if (nav) {
        nav.classList.toggle('mobile-open');
    }
}

// Initialize page-specific functionality
function initializePage() {
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';
    
    switch (currentPage) {
        case 'index.html':
        case '':
            loadFeaturedBooks();
            break;
        case 'books.html':
            loadAllBooks();
            break;
        case 'cart.html':
            loadCartItems();
            break;
    }
}

// Event listeners
document.addEventListener('DOMContentLoaded', function() {
    initializePage();
    
    // Newsletter form
    const newsletterForm = document.getElementById('newsletterForm');
    if (newsletterForm) {
        newsletterForm.addEventListener('submit', handleNewsletterSubmit);
    }
    
    // Contact form
    const contactForm = document.getElementById('contactForm');
    if (contactForm) {
        contactForm.addEventListener('submit', handleContactSubmit);
    }
    
    // Clear cart button
    const clearCartBtn = document.getElementById('clearCartBtn');
    if (clearCartBtn) {
        clearCartBtn.addEventListener('click', clearCart);
    }
    
    // Mobile menu toggle
    const mobileMenuToggle = document.getElementById('mobileMenuToggle');
    if (mobileMenuToggle) {
        mobileMenuToggle.addEventListener('click', toggleMobileMenu);
    }
});

// Update cart badge on page load
window.addEventListener('load', function() {
    cartManager.updateCartBadge();
});