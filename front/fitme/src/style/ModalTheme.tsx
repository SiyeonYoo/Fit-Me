import type { CustomFlowbiteTheme } from 'flowbite-react';

const customModalTheme: CustomFlowbiteTheme['modal'] = {
  root: {
    base: 'fixed inset-x-0 top-0 z-50 h-screen overflow-y-auto overflow-x-hidden md:inset-0 md:h-full',
    show: {
      on: 'flex bg-gray-900 bg-opacity-50',
      off: 'hidden',
    },
    sizes: {
      sm: 'max-w-sm',
      md: 'max-w-md',
      lg: 'max-w-lg',
      xl: 'max-w-xl',
      '2xl': 'max-w-2xl',
      '3xl': 'max-w-3xl',
      '4xl': 'max-w-4xl',
      '5xl': 'max-w-5xl',
      '6xl': 'max-w-6xl',
      '7xl': 'max-w-7xl',
    },
    positions: {
      'top-left': 'items-start justify-start',
      'top-center': 'items-start justify-center',
      'top-right': 'items-start justify-end',
      'center-left': 'items-center justify-start',
      center: 'items-center justify-center',
      'center-right': 'items-center justify-end',
      'bottom-right': 'items-end justify-end',
      'bottom-center': 'items-end justify-center',
      'bottom-left': 'items-end justify-start',
    },
  },
  content: {
    base: 'relative h-full w-full p-4 md:h-auto',
    inner: 'relative flex max-h-[90dvh] flex-col rounded-lg bg-white shadow',
  },
  body: {
    base: 'flex-1 overflow-auto p-6',
    popup: 'pt-0',
  },
  header: {
    base: 'flex items-start justify-between rounded-t border-b p-5',
    popup: 'border-b-0 p-2',
    title: 'text-xl font-medium text-gray-900',
    close: {
      base: 'ml-auto inline-flex items-center rounded-lg bg-transparent p-1.5 text-sm text-gray-400 hover:bg-gray-200 hover:text-gray-900',
      icon: 'h-5 w-5',
    },
  },
  footer: {
    base: 'flex items-center space-x-2 rounded-b border-gray-200 p-6',
    popup: 'border-t',
  },
};

export default customModalTheme;