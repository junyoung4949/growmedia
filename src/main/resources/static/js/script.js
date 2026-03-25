/* ===========================
   GROW MEDIA — script.js
=========================== */

(function () {
  'use strict';

  /* ─── HEADER SCROLL ─── */
  const header = document.getElementById('header');

  function handleScroll() {
    if (window.scrollY > 60) {
      header.classList.add('scrolled');
    } else {
      header.classList.remove('scrolled');
    }
  }
  window.addEventListener('scroll', handleScroll, { passive: true });

  /* ─── HAMBURGER MENU ─── */
  const hamburger = document.getElementById('hamburger');
  const mobileMenu = document.getElementById('mobileMenu');
  let menuOpen = false;

  hamburger.addEventListener('click', () => {
    menuOpen = !menuOpen;
    mobileMenu.classList.toggle('open', menuOpen);
    document.body.style.overflow = menuOpen ? 'hidden' : '';

    // Animate hamburger lines
    const spans = hamburger.querySelectorAll('span');
    if (menuOpen) {
      spans[0].style.transform = 'rotate(45deg) translate(5px, 5px)';
      spans[1].style.opacity = '0';
      spans[2].style.transform = 'rotate(-45deg) translate(5px, -5px)';
    } else {
      spans.forEach(s => { s.style.transform = ''; s.style.opacity = ''; });
    }
  });

  document.querySelectorAll('.mobile-link').forEach((link) => {
    link.addEventListener('click', () => {
      menuOpen = false;
      mobileMenu.classList.remove('open');
      document.body.style.overflow = '';
      const spans = hamburger.querySelectorAll('span');
      spans.forEach(s => { s.style.transform = ''; s.style.opacity = ''; });
    });
  });

  /* ─── SCROLL-TRIGGERED ANIMATIONS (AOS-like) ─── */
  const aosElements = document.querySelectorAll('[data-aos]');

  function checkAOS() {
    const windowHeight = window.innerHeight;
    aosElements.forEach((el) => {
      const rect = el.getBoundingClientRect();
      const delay = parseInt(el.getAttribute('data-aos-delay') || 0);
      if (rect.top < windowHeight - 60) {
        setTimeout(() => {
          el.classList.add('aos-animate');
        }, delay);
      }
    });
  }

  window.addEventListener('scroll', checkAOS, { passive: true });
  // Initial check
  setTimeout(checkAOS, 100);

  /* ─── COUNTER ANIMATION ─── */
  function animateCounter(el) {
    const target = parseInt(el.getAttribute('data-target'));
    const suffix = el.querySelector('span') ? el.querySelector('span').outerHTML : '';
    const duration = 1600;
    const startTime = performance.now();

    function update(currentTime) {
      const elapsed = currentTime - startTime;
      const progress = Math.min(elapsed / duration, 1);
      // Ease-out cubic
      const eased = 1 - Math.pow(1 - progress, 3);
      const current = Math.floor(eased * target);
      el.innerHTML = current + suffix;
      if (progress < 1) requestAnimationFrame(update);
      else el.innerHTML = target + suffix;
    }

    requestAnimationFrame(update);
  }

  const counterObserver = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        animateCounter(entry.target);
        counterObserver.unobserve(entry.target);
      }
    });
  }, { threshold: 0.5 });

  document.querySelectorAll('.stat-num, .card-num').forEach((el) => {
    if (el.getAttribute('data-target')) {
      counterObserver.observe(el);
    }
  });

  /* ─── SMOOTH SCROLL ─── */
  document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
    anchor.addEventListener('click', function (e) {
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        e.preventDefault();
        const headerHeight = header.offsetHeight;
        const targetPos = target.getBoundingClientRect().top + window.scrollY - headerHeight;
        window.scrollTo({ top: targetPos, behavior: 'smooth' });
      }
    });
  });

  /* ─── FORM SUBMIT ─── */
  const submitBtn = document.getElementById('submitBtn');
  if (submitBtn) {
    submitBtn.addEventListener('click', () => {
      const inputs = document.querySelectorAll('.form-input, .form-textarea');
      let hasValue = false;
      inputs.forEach(input => { if (input.value.trim()) hasValue = true; });

      if (!hasValue) {
        submitBtn.textContent = '내용을 입력해 주세요 ✕';
        submitBtn.style.background = '#ffeded';
        submitBtn.style.color = '#c0392b';
        setTimeout(() => {
          submitBtn.textContent = '무료 상담 신청하기 →';
          submitBtn.style.background = '';
          submitBtn.style.color = '';
        }, 2000);
        return;
      }

      submitBtn.textContent = '전송 중...';
      submitBtn.disabled = true;

      setTimeout(() => {
        submitBtn.textContent = '신청 완료! 빠르게 연락드리겠습니다 ✓';
        submitBtn.style.background = '#e8f5e9';
        submitBtn.style.color = '#1b7a34';
        inputs.forEach(input => (input.value = ''));
        setTimeout(() => {
          submitBtn.textContent = '무료 상담 신청하기 →';
          submitBtn.style.background = '';
          submitBtn.style.color = '';
          submitBtn.disabled = false;
        }, 3000);
      }, 1200);
    });
  }

  /* ─── PORTFOLIO SLIDER ─── */
  const track = document.getElementById('sliderTrack');
  const prevBtn = document.getElementById('slidePrev');
  const nextBtn = document.getElementById('slideNext');
  const dotsWrap = document.getElementById('sliderDots');

  if (track && prevBtn && nextBtn) {
    const items = track.querySelectorAll('.slide-item');
    const ITEM_WIDTH = 360 + 24; // card width + gap
    let currentIndex = 0;
    let visibleCount = Math.floor(track.parentElement.offsetWidth / ITEM_WIDTH) || 1;
    const maxIndex = Math.max(0, items.length - visibleCount);

    // Create dots
    items.forEach((_, i) => {
      const dot = document.createElement('button');
      dot.className = 'slider-dot' + (i === 0 ? ' active' : '');
      dot.setAttribute('aria-label', `슬라이드 ${i + 1}`);
      dot.addEventListener('click', () => goTo(i));
      dotsWrap.appendChild(dot);
    });

    function updateDots() {
      dotsWrap.querySelectorAll('.slider-dot').forEach((d, i) => {
        d.classList.toggle('active', i === currentIndex);
      });
    }

    function goTo(index) {
      currentIndex = Math.max(0, Math.min(index, maxIndex));
      track.style.transform = `translateX(-${currentIndex * ITEM_WIDTH}px)`;
      updateDots();
    }

    prevBtn.addEventListener('click', () => goTo(currentIndex - 1));
    nextBtn.addEventListener('click', () => goTo(currentIndex + 1));

    // Drag / swipe
    let startX = 0, isDragging = false, dragOffset = 0;

    track.addEventListener('mousedown', (e) => {
      isDragging = true; startX = e.clientX;
      track.style.transition = 'none';
    });
    document.addEventListener('mousemove', (e) => {
      if (!isDragging) return;
      dragOffset = e.clientX - startX;
      track.style.transform = `translateX(${-currentIndex * ITEM_WIDTH + dragOffset}px)`;
    });
    document.addEventListener('mouseup', () => {
      if (!isDragging) return;
      isDragging = false;
      track.style.transition = '';
      if (dragOffset < -60) goTo(currentIndex + 1);
      else if (dragOffset > 60) goTo(currentIndex - 1);
      else goTo(currentIndex);
      dragOffset = 0;
    });

    // Touch
    track.addEventListener('touchstart', (e) => { startX = e.touches[0].clientX; }, { passive: true });
    track.addEventListener('touchend', (e) => {
      const diff = e.changedTouches[0].clientX - startX;
      if (diff < -50) goTo(currentIndex + 1);
      else if (diff > 50) goTo(currentIndex - 1);
    });

    // Keyboard
    document.addEventListener('keydown', (e) => {
      if (e.key === 'ArrowLeft') goTo(currentIndex - 1);
      if (e.key === 'ArrowRight') goTo(currentIndex + 1);
    });

    // Resize
    window.addEventListener('resize', () => {
      visibleCount = Math.floor(track.parentElement.offsetWidth / ITEM_WIDTH) || 1;
      goTo(Math.min(currentIndex, Math.max(0, items.length - visibleCount)));
    });
  }

  /* ─── TICKER PAUSE ON HOVER ─── */
  const tickerTrack = document.querySelector('.ticker-track');
  if (tickerTrack) {
    tickerTrack.addEventListener('mouseenter', () => {
      tickerTrack.style.animationPlayState = 'paused';
    });
    tickerTrack.addEventListener('mouseleave', () => {
      tickerTrack.style.animationPlayState = 'running';
    });
  }

  /* ─── ACTIVE NAV HIGHLIGHT ON SCROLL ─── */
  const sections = document.querySelectorAll('section[id]');
  const navLinks = document.querySelectorAll('.nav-links a[href^="#"]');

  function highlightNav() {
    let current = '';
    sections.forEach((section) => {
      const sectionTop = section.offsetTop - 100;
      if (window.scrollY >= sectionTop) {
        current = section.getAttribute('id');
      }
    });
    navLinks.forEach((link) => {
      link.style.color = '';
      if (link.getAttribute('href') === '#' + current) {
        link.style.color = 'var(--sky)';
      }
    });
  }

  window.addEventListener('scroll', highlightNav, { passive: true });

})();