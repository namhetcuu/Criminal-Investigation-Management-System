import { Button } from '@/components/ui/button';
import { MessageCircle, Users, Shield } from 'lucide-react';
import React from 'react';
import Link from 'next/link';

export const CanHelp: React.FC = () => {
    const helpItems = [
        {
            icon: MessageCircle,
            title: 'Tell us what happened.',
            description: 'Report incidents and crimes to help us serve you better.'
        },
        {
            icon: Users,
            title: 'Your contribution & our mission.',
            description: 'Work with us to build stronger, safer communities.'
        },
        {
            icon: Shield,
            title: 'Protect yourself and others.',
            description: 'Learn safety tips and crime prevention strategies.'
        }
    ];

    return (
        <section className="py-16 bg-white">
            <div className="container mx-auto px-4">
                <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">
                    How You Can Help?
                </h2>

                <div className="grid md:grid-cols-3 gap-12 max-w-5xl mx-auto mb-12">
                    {helpItems.map((item, index) => (
                        <div key={index} className="text-center group">
                            <div className="w-20 h-20 bg-black rounded-lg flex items-center justify-center mx-auto mb-6 group-hover:bg-blue-900 transition-colors duration-300 shadow-lg">
                                <item.icon className="w-10 h-10 text-white" />
                            </div>
                            <h3 className="font-semibold text-gray-900 text-lg mb-3">
                                {item.title}
                            </h3>
                            <p className="text-gray-600 text-sm leading-relaxed">
                                {item.description}
                            </p>
                        </div>
                    ))}
                </div>

                <div className="text-center">
                    <Link href="/reporter">
                        <Button
                            size="lg"
                            className="cursor-pointer bg-blue-700 hover:bg-blue-800 text-white px-12 py-6 text-lg font-semibold rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 transform hover:scale-105"
                        >
                            File A Report
                        </Button>
                    </Link>

                </div>
            </div>
        </section>
    );
};