// Utility functions for cart management
function getCart() {
  return JSON.parse(localStorage.getItem('cartItems')) || [];
}

function saveCart(cart) {
  localStorage.setItem('cartItems', JSON.stringify(cart));
}

function updateCartBadge() {
  const badge = document.querySelector('.cart-count');
  if (!badge) return;
  const count = getCart().reduce((sum, item) => sum + item.quantity, 0);
  badge.textContent = count;
}

function addToCart(item) {
  const cart = getCart();
  const existing = cart.find((b) => b.id === item.id);
  if (existing) {
    existing.quantity += 1;
  } else {
    cart.push(item);
  }
  saveCart(cart);
}

function removeItem(id) {
  let cart = getCart();
  cart = cart.filter((item) => item.id !== id);
  saveCart(cart);
}

function changeQty(id, delta) {
  const cart = getCart();
  const item = cart.find((b) => b.id === id);
  if (!item) return;
  item.quantity += delta;
  if (item.quantity <= 0) {
    removeItem(id);
  } else {
    saveCart(cart);
  }
}

function clearCart() {
  localStorage.removeItem('cartItems');
}

// Render cart items on cart.html
function renderCart() {
  const cartContainer = document.getElementById('cart-items');
  const totalContainer = document.getElementById('cart-total');
  if (!cartContainer || !totalContainer) return;

  const cart = getCart();
  if (cart.length === 0) {
    cartContainer.innerHTML = '<p>Your cart is empty</p>';
    totalContainer.textContent = '';
    return;
  }

  cartContainer.innerHTML = '';
  cart.forEach((item) => {
    const div = document.createElement('div');
    div.className = 'cart-item';
    div.innerHTML = `
      <img src="${item.image}" alt="${item.title}">
      <div>
        <h4>${item.title}</h4>
        <p>$${item.price.toFixed(2)}</p>
      </div>
      <div class="qty-controls">
        <button class="decrease" data-id="${item.id}">-</button>
        <span>${item.quantity}</span>
        <button class="increase" data-id="${item.id}">+</button>
      </div>
      <div>$${(item.price * item.quantity).toFixed(2)}</div>
      <i class="fa fa-times remove-item" data-id="${item.id}"></i>
    `;
    cartContainer.appendChild(div);
  });

  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
  totalContainer.textContent = `Total: $${total.toFixed(2)}`;

  // Attach events
  cartContainer.querySelectorAll('.increase').forEach((btn) => {
    btn.addEventListener('click', () => {
      changeQty(btn.dataset.id, 1);
      renderCart();
      updateCartBadge();
    });
  });

  cartContainer.querySelectorAll('.decrease').forEach((btn) => {
    btn.addEventListener('click', () => {
      changeQty(btn.dataset.id, -1);
      renderCart();
      updateCartBadge();
    });
  });

  cartContainer.querySelectorAll('.remove-item').forEach((btn) => {
    btn.addEventListener('click', () => {
      removeItem(btn.dataset.id);
      renderCart();
      updateCartBadge();
    });
  });
}

// DOM Ready
window.addEventListener('DOMContentLoaded', () => {
  updateCartBadge();

  // Newsletter form (index.html)
  const newsletterForm = document.getElementById('newsletter-form');
  if (newsletterForm) {
    newsletterForm.addEventListener('submit', (e) => {
      e.preventDefault();
      const emailInput = newsletterForm.querySelector('input[type="email"]');
      if (emailInput.value.trim() === '') {
        alert('Please enter a valid email');
        return;
      }
      alert('Thank you for subscribing!');
      emailInput.value = '';
    });
  }

  // Contact form validation (contact.html)
  const contactForm = document.getElementById('contact-form');
  if (contactForm) {
    contactForm.addEventListener('submit', (e) => {
      e.preventDefault();
      const name = contactForm.querySelector('input[name="name"]').value.trim();
      const email = contactForm.querySelector('input[name="email"]').value.trim();
      const message = contactForm.querySelector('textarea[name="message"]').value.trim();
      if (!name || !email || !message) {
        alert('Please fill in all fields');
        return;
      }
      alert('Message sent successfully!');
      contactForm.reset();
    });
  }

  // Add-to-cart buttons (books.html)
  document.querySelectorAll('.add-to-cart').forEach((btn) => {
    btn.addEventListener('click', () => {
      const { id, title, price, image } = btn.dataset;
      addToCart({ id, title, price: Number(price), image, quantity: 1 });
      alert('Book added to cart');
      updateCartBadge();
    });
  });

  // Cart page render & clear
  if (document.getElementById('cart-items')) {
    renderCart();
    const clearBtn = document.getElementById('clear-cart');
    if (clearBtn) {
      clearBtn.addEventListener('click', () => {
        if (confirm('Clear all items?')) {
          clearCart();
          renderCart();
          updateCartBadge();
        }
      });
    }
  }
});