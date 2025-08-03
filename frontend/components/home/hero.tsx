"use client";

import React, { useState, useEffect } from 'react';

export const Hero = () => {
  const slides = [
    '/images/banner1.png',
    '/images/banner2.png',
    '/images/banner3.png',
  ];

  const [currentSlide, setCurrentSlide] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentSlide((prev) => (prev + 1) % slides.length);
    }, 5000);

    return () => clearInterval(timer);
  }, [slides.length]);

  return (
    <section>
      <div className="container mx-auto px-4">
        <div
          className="relative bg-gray-900 rounded-lg overflow-hidden shadow-xl mx-auto"
          style={{ height: '415px', maxWidth: '915px', width: '100%' }}
        >
          <div className="relative w-full h-full">
            <img
              src={slides[currentSlide]}
              alt={`NYPD Officers Slide ${currentSlide + 1}`}
              className="w-full h-full object-cover transition-opacity duration-1000"
            />

            {/* Gradient Overlay */}
            <div className="absolute inset-0 bg-gradient-to-r from-black/70 to-transparent z-10"></div>

            {/* Hero Content Overlay */}
            <div className="absolute inset-0 z-20 flex items-center justify-center pl-12">
              {/* Add content here if needed */}
            </div>
          </div>

          {/* Carousel Indicators
          <div className="absolute bottom-6 left-1/2 transform -translate-x-1/2 flex space-x-3 z-30">
            {slides.map((_, index) => (
              <button
                key={index}
                onClick={() => setCurrentSlide(index)}
                className={`w-3 h-3 rounded-full transition-all duration-300 ${
                  index === currentSlide
                    ? 'bg-white scale-110'
                    : 'bg-white/50 hover:bg-white/75'
                }`}
                aria-label={`Go to slide ${index + 1}`}
              />
            ))}
          </div> */}
        </div>
      </div>
    </section>
  );
};