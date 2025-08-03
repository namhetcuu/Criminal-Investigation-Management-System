// Step1.tsx
'use client'
import { useState } from 'react';
import Image from 'next/image';

export default function Step3({ data, onNext }: any) {
    const [form, setForm] = useState(data);

    const handleChange = (e: any) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = () => {
        onNext(form);
    };

    return (
        <div>
            <main className="container mx-auto px-4 pb-8">
                <div className="flex flex-col justify-center items-center">
                    <Image
                        className="mt-10 mb-10 ml-10"
                        src="/images/image 11.svg"
                        alt="image"
                        width={160}
                        height={160}
                    />
                    <div className="w-100 h-10 text-center mb-30">
                        <p className="text-wrap">
                            Thank you for your submission.
                        </p>
                    </div>
                </div>
            </main>
        </div>
    );
}