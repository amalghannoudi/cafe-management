import { TestBed } from '@angular/core/testing';
import { HTTP_INTERCEPTORS, HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { TokenInterceptor } from './token-interceptor.interceptor';

describe('TokenInterceptor', () => {
  let interceptor: HttpInterceptor;
  let mockHttpRequest: HttpRequest<any>;
  let mockHttpHandler: HttpHandler;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {
          provide: HTTP_INTERCEPTORS,
          useClass: TokenInterceptor,
          multi: true,
        },
      ],
    });

    interceptor = TestBed.inject(TokenInterceptor);
    mockHttpRequest = {} as HttpRequest<any>;
    mockHttpHandler = {} as HttpHandler;
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  it('should intercept the request', () => {
    spyOn(mockHttpHandler, 'handle').and.callThrough();

    interceptor.intercept(mockHttpRequest, mockHttpHandler);

    expect(mockHttpHandler.handle).toHaveBeenCalledWith(mockHttpRequest);
  });
});
