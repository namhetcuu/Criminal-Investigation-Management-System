import { Card, CardContent } from '@/components/ui/card';
import React from 'react';
const cardData = [
  {
    title: 'CompStat & Crime Stats',
    description: 'Access crime statistics, traffic data, reports, and CompStat 2.0, an advanced digital crime-tracking system that delivers block-by-block data.',
    color: 'bg-red-500',
    image: '/images/crime-stats.png',
  },
  {
    title: 'Body-worn Cameras',
    description: 'Body-worn cameras have come to the NYPD. What you need to know about this important transparency initiative.',
    color: 'bg-orange-500',
    image: '/images/crime-stats1.png',
  },
  {
    title: 'Help is Available',
    description: 'Before cops can help others, they must first take care of themselves. Mental health resources and support are available.',
    color: 'bg-blue-500',
    image: '/images/crime-stats2.png',
  },
];

export const ProgramsResources: React.FC = () => {
  return (
    <section className="py-16 bg-white ">
      <div className="container mx-auto px-4">
        <h2 className="text-4xl font-bold text-center text-gray-900 mb-12">
          Programs and Resources
        </h2>

        <div className="grid gap-8 lg:grid-cols-3 max-w-6xl mx-auto">
          {cardData.map((card, index) => (
            <Card
              key={index}
              className="cursor-pointer overflow-hidden shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-2 bg-white"
            >
              <CardContent className="p-0">
                {/* Image Section */}
                <div className="relative h-100 bg-gray-100 flex items-center justify-center overflow-hidden">
                  <img
                    src={card.image}
                    alt={card.title}
                    className="w-full h-full object-cover hover:scale-105 transition-transform duration-300"
                  />
                </div>

                {/* Content Section */}
                <div className="p-6">
                  <h3 className="text-xl font-bold text-blue-600 mb-3 hover:text-blue-800 transition-colors">
                    {card.title}
                  </h3>
                  <p className="text-gray-600 leading-relaxed text-sm">
                    {card.description}
                  </p>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  );
};