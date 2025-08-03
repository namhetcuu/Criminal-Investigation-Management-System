import Image from 'next/image';
import { Button } from '@/components/ui/button';
import Link from 'next/link';

export default function Home() {
  return (
    <>
      {/* Hero Slider Section */}
      <section className='relative bg-gray-100 min-h-[500px] flex items-center justify-center'>
        <div className='relative w-full max-w-6xl mx-auto'>
          <Image
            src='/SC_001/FrameSlider.png'
            alt='NYPD Officers'
            width={1200}
            height={500}
            className='w-full h-[500px] object-cover rounded-lg'
            priority
          />
          {/* Slider dots */}
          <div className='absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2'>
            <div className='w-3 h-3 bg-white rounded-full'></div>
            <div className='w-3 h-3 bg-gray-400 rounded-full'></div>
            <div className='w-3 h-3 bg-gray-400 rounded-full'></div>
          </div>
        </div>
      </section>

      {/* How You Can Help Section */}
      <section className='py-16 bg-white'>
        <div className='container mx-auto px-4'>
          <h2 className='text-4xl font-bold text-center text-gray-800 mb-12'>
            How You Can Help?
          </h2>

          <div className='grid grid-cols-1 md:grid-cols-3 gap-8 max-w-4xl mx-auto'>
            {/* Tell us what happened */}
            <div className='text-center'>
              <div className='bg-gray-800 rounded-lg p-6 w-20 h-20 mx-auto mb-6 flex items-center justify-center'>
                <Image
                  src='/SC_001/comment.png'
                  alt='Comment icon'
                  width={40}
                  height={40}
                  className='filter invert'
                />
              </div>
              <h3 className='text-xl font-semibold mb-4'>
                Tell us what happened.
              </h3>
            </div>

            {/* Your contribution & our mission */}
            <div className='text-center'>
              <div className='bg-gray-800 rounded-lg p-6 w-20 h-20 mx-auto mb-6 flex items-center justify-center'>
                <Image
                  src='/SC_001/partner_exchange.png'
                  alt='Partnership icon'
                  width={40}
                  height={40}
                  className='filter invert'
                />
              </div>
              <h3 className='text-xl font-semibold mb-4'>
                Your contribution & our mission.
              </h3>
            </div>

            {/* Protect yourself and others */}
            <div className='text-center'>
              <div className='bg-gray-800 rounded-lg p-6 w-20 h-20 mx-auto mb-6 flex items-center justify-center'>
                <Image
                  src='/SC_001/health_and_safety.png'
                  alt='Safety icon'
                  width={40}
                  height={40}
                  className='filter invert'
                />
              </div>
              <h3 className='text-xl font-semibold mb-4'>
                Protect yourself and others.
              </h3>
            </div>
          </div>

          {/* File A Report Button */}
          <div className='text-center mt-12'>
            <Link
              href={'/reporter'}
              className='bg-blue-600 hover:bg-blue-700 text-white px-8 py-3 text-lg font-semibold'
            >
              File A Report
            </Link>
          </div>
        </div>
      </section>

      {/* Programs and Resources Section */}
      <section className='py-16 bg-gray-50'>
        <div className='container mx-auto px-4'>
          <h2 className='text-4xl font-bold text-center text-gray-800 mb-12'>
            Programs and Resources
          </h2>

          <div className='grid grid-cols-1 md:grid-cols-3 gap-8 max-w-6xl mx-auto'>
            {/* CompStat & Crime Stats */}
            <div className='bg-white rounded-lg shadow-md overflow-hidden'>
              <div className='h-48 bg-gradient-to-r from-red-500 via-orange-500 via-cyan-500 to-yellow-500 flex items-center justify-center'>
                <div className='text-white text-center'>
                  <div className='space-y-2'>
                    <div className='bg-red-600 text-white px-4 py-2 transform -rotate-12'>
                      COMPUTER
                    </div>
                    <div className='bg-orange-500 text-white px-4 py-2 transform rotate-12'>
                      ROBBERIES
                    </div>
                    <div className='bg-cyan-500 text-white px-4 py-2 transform -rotate-12'>
                      STATISTICS
                    </div>
                    <div className='bg-yellow-500 text-white px-4 py-2 transform rotate-12'>
                      BURGLARY
                    </div>
                  </div>
                </div>
              </div>
              <div className='p-6'>
                <h3 className='text-xl font-bold mb-4 text-blue-600'>
                  CompStat & Crime Stats
                </h3>
                <p className='text-gray-600'>
                  Access crime statistics, traffic data, reports, and CompStat
                  2.0, an advanced digital crime-tracking system that delivers
                  block-by-block data.
                </p>
              </div>
            </div>

            {/* Body-worn Cameras */}
            <div className='bg-white rounded-lg shadow-md overflow-hidden'>
              <div className='h-48'>
                <Image
                  src='/SC_001/image2.png'
                  alt='Body-worn camera equipment'
                  width={400}
                  height={200}
                  className='w-full h-full object-cover'
                />
              </div>
              <div className='p-6'>
                <h3 className='text-xl font-bold mb-4 text-blue-600'>
                  Body-worn Cameras
                </h3>
                <p className='text-gray-600'>
                  Body-worn cameras have come to the NYPD. What you need to
                  know.
                </p>
              </div>
            </div>

            {/* Help Is Available */}
            <div className='bg-white rounded-lg shadow-md overflow-hidden'>
              <div className='h-48'>
                <Image
                  src='/SC_001/image3.png'
                  alt='NYPD Badge'
                  width={400}
                  height={200}
                  className='w-full h-full object-cover'
                />
              </div>
              <div className='p-6'>
                <h3 className='text-xl font-bold mb-4 text-blue-600'>
                  Help Is Available
                </h3>
                <p className='text-gray-600'>
                  Before cops can help others, they must first take care of
                  themselves. Help is available.
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
}
